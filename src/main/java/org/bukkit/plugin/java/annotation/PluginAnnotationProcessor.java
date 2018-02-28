package org.bukkit.plugin.java.annotation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.LoadBefore;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LoadOn;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.UsesDatabase;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;


import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.swing.text.DateFormatter;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("org.bukkit.plugin.java.annotation.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PluginAnnotationProcessor extends AbstractProcessor {

    private boolean hasMainBeenFound = false;

    private static final DateTimeFormatter dFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);

    @Override
    public boolean process(Set<? extends TypeElement> annots, RoundEnvironment rEnv) {
        Element main = null;
        hasMainBeenFound = false;

        Set<? extends Element> elements = rEnv.getElementsAnnotatedWith(Plugin.class);
        if(elements.size() > 1) {
            raiseError("Found more than one plugin main class");
            return false;
        }

        if(elements.isEmpty()) {
            return false;
        }
        if(hasMainBeenFound){
            raiseError("The plugin class has already been located, aborting!");
            return false;
        }
        main = elements.iterator().next();
        hasMainBeenFound = true;

        TypeElement mainType;
        if(main instanceof TypeElement){
            mainType = (TypeElement) main;
        } else {
            raiseError("Element annotated with @Main is not a type!", main);
            return false;
        }

        if(!(mainType.getEnclosingElement() instanceof PackageElement) && !mainType.getModifiers().contains(Modifier.STATIC)){
            raiseError("Element annotated with @Main is not top-level or static nested!", mainType);
            return false;
        }

        if(!processingEnv.getTypeUtils().isSubtype(mainType.asType(), fromClass(JavaPlugin.class))){
            raiseError("Class annotated with @Main is not an subclass of JavaPlugin!", mainType);
        }

        Map<String, Object> yml = Maps.newLinkedHashMap(); // linked so we can maintain the same output into file for sanity

        // populate mainName
        final String mainName = mainType.getQualifiedName().toString();
        yml.put("main", mainName); // always override this so we make sure the main class name is correct

        // populate plugin name
        processAndPut(yml, "name", mainType, mainName.substring(mainName.lastIndexOf('.') + 1), Plugin.class, String.class, "name");

        // populate version
        processAndPut(yml, "version", mainType, Plugin.DEFAULT_VERSION, Plugin.class, String.class, "version");

        // populate plugin description
        processAndPut(yml, "description", mainType, null, Description.class, String.class, "desc");

        // populate plugin load order
        processAndPut(yml, "load", mainType, null, LoadOn.class, String.class,"loadOn");

        // authors
        Author[] authors = mainType.getAnnotationsByType(Author.class);
        List<String> authorMap = Lists.newArrayList();
        for(Author auth : authors) {
            authorMap.add(auth.name());
        }
        if(authorMap.size() > 1) {
            yml.put("authors", authorMap);
        } else if(authorMap.size() == 1) {
            yml.put("author", authorMap.iterator().next());
        }

        // website
        processAndPut(yml, "website", mainType, null, Website.class, String.class, "url");

        // prefix
        processAndPut(yml, "prefix", mainType, null, LogPrefix.class, String.class, "prefix");

        // dependencies
        Dependency[] dependencies = mainType.getAnnotationsByType(Dependency.class);
        List<String> hardDependencies = Lists.newArrayList();
        for(Dependency dep : dependencies) {
            hardDependencies.add(dep.plugin());
        }
        if(!hardDependencies.isEmpty()) yml.putIfAbsent("depend", hardDependencies);

        // soft-dependencies
        SoftDependency[] softDependencies = mainType.getAnnotationsByType(SoftDependency.class);
        String[] softDepArr = new String[softDependencies.length];
        for(int i = 0; i < softDependencies.length; i++) {
            softDepArr[i] = softDependencies[i].plugin();
        }
        if(softDepArr.length > 0) yml.putIfAbsent("softdepend", softDepArr);

        // load-before
        LoadBefore[] loadBefore = mainType.getAnnotationsByType(LoadBefore.class);
        String[] loadBeforeArr = new String[loadBefore.length];
        for(int i = 0; i < loadBefore.length; i++) {
            loadBeforeArr[i] = loadBefore[i].plugin();
        }
        if(loadBeforeArr.length > 0) yml.putIfAbsent("loadbefore", loadBeforeArr);

        // commands
        Command[] commands = mainType.getAnnotationsByType(Command.class);
        Map<String, Object> commandMap = Maps.newLinkedHashMap();
        for(Command command : commands) {
            Map<String, Object> desc = Maps.newLinkedHashMap();
            String name = command.name();
            if(!command.desc().isEmpty()) desc.put("description", command.desc());
            if(command.aliases().length != 0) desc.put("aliases", command.aliases());
            if(!command.permission().isEmpty()) desc.put("permission", command.permission());
            if(!command.permissionMessage().isEmpty()) desc.put("permission-message", command.permissionMessage());
            if(!command.usage().isEmpty()) desc.put("usage", command.usage());
            commandMap.put(name, desc);
        }
        if(!commandMap.isEmpty()) yml.putIfAbsent("commands", commandMap);

        // permissions
        Permission[] permissions = mainType.getAnnotationsByType(Permission.class);
        Map<String, Object> permMap = Maps.newLinkedHashMap();
        for(Permission perm : permissions) {
            Map<String, Object> desc = Maps.newLinkedHashMap();
            String name = perm.name();
            if(!perm.desc().isEmpty()) desc.put("description", perm.desc());
            desc.put("default", perm.defaultValue().toString());
            Map<String, Object> children = Maps.newLinkedHashMap();
            for(ChildPermission child : perm.children()) {
                children.put(child.name(), child.inherit());
            }
            if(!children.isEmpty()) desc.put("children", children);
            permMap.put(name, desc);
        }
        if(!permMap.isEmpty()) yml.putIfAbsent("permissions", permMap);

        // database D: //TODO: Remove me!
        if(mainType.getAnnotation(UsesDatabase.class) != null) {
            yml.put("database", true);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Database support was dropped in Bukkit in version 1.12.", mainType);
        }

        Yaml yaml = new Yaml();
        try {
            FileObject file = this.processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "plugin.yml");
            try(Writer w = file.openWriter()) {
                w.append("# Auto-generated plugin.yml, generated at ")
                 .append(LocalDateTime.now().format(dFormat))
                 .append(" by ")
                 .append(this.getClass().getName())
                 .append("\n\n");
                // have to format the yaml explicitly because otherwise it dumps child nodes as maps within braces.
                String raw = yaml.dumpAs(yml, Tag.MAP, DumperOptions.FlowStyle.BLOCK);
                w.write(raw);
                w.flush();
                w.close();
            }
            // try with resources will close the Writer since it implements Closeable
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "NOTE: You are using org.bukkit.plugin.java.annotation, an experimental API!");
        return true;
    }

    private void raiseError(String message) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

    private void raiseError(String message, Element element) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private TypeMirror fromClass(Class<?> clazz) {
        return processingEnv.getElementUtils().getTypeElement(clazz.getName()).asType();
    }

    private <A extends Annotation, R> R processAndPut(
            Map<String, Object> map, String name, Element el, R defaultVal, Class<A> annotationType, Class<R> returnType) {
        return processAndPut(map, name, el, defaultVal, annotationType, returnType, "value");
    }

    private <A extends Annotation, R> R processAndPut(
            Map<String, Object> map, String name, Element el, R defaultVal, Class<A> annotationType, Class<R> returnType, String methodName) {
        R result = process(el, defaultVal, annotationType, returnType, methodName);
        if(result != null)
            map.putIfAbsent(name, result);
        return result;
    }

    private <A extends Annotation, R> R process(Element el, R defaultVal, Class<A> annotationType, Class<R> returnType, String methodName) {
        R result;
        A ann = el.getAnnotation(annotationType);
        if(ann == null) result = defaultVal;
        else {
            try {
                Method value = annotationType.getMethod(methodName);
                Object res = value.invoke(ann);
                result = (R) (returnType == String.class ? res.toString() : returnType.cast(res));
            } catch (Exception e) {
                throw new RuntimeException(e); // shouldn't happen in theory (blame Choco if it does)
            }
        }
        return result;
    }
}
