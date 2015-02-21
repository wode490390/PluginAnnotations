package org.bukkit.plugin.java.annotation;

import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("org.bukkit.plugin.java.annotation.*")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class PluginAnnotationProcessor extends AbstractProcessor {

    private boolean hasMainBeenFound = false;

    private static final DateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public boolean process(Set<? extends TypeElement> annots, RoundEnvironment rEnv) {
        Element main = null;
        for(Element el : rEnv.getElementsAnnotatedWith(Main.class)) {
            if(main != null){
                raiseError("More than one class with @Main found, aborting!");
                return false;
            }
            main = el;
        }

        if(main == null) return false;

        if(hasMainBeenFound){
            raiseError("More than one class with @Main found, aborting!");
            return false;
        }
        hasMainBeenFound = true;

        TypeElement mainType;
        if(main instanceof TypeElement){
            mainType = (TypeElement) main;
        } else {
            raiseError("Element annotated with @Main is not a type!");
            return false;
        }

        if(!(mainType.getEnclosingElement() instanceof PackageElement) && !mainType.getModifiers().contains(Modifier.STATIC)){
            raiseError("Element annotated with @Main is not top-level or static nested!");
            return false;
        }

        if(!processingEnv.getTypeUtils().isSubtype(mainType.asType(), fromClass(JavaPlugin.class))){
            raiseError("Class annotated with @Main is not an subclass of JavaPlugin!");
        }

        Map<String, Object> yml = new HashMap<String, Object>();

        final String mainName = mainType.getQualifiedName().toString();
        yml.put("main", mainName);

        processAndPut(yml, "name", mainType, mainName.substring(mainName.lastIndexOf('.') + 1), Name.class, String.class);

        processAndPut(yml, "version", mainType, Version.DEFAULT_VERSION, Version.class, String.class);

        processAndPut(yml, "description", mainType, null, Description.class, String.class);

        processAndPut(yml, "load", mainType, null, LoadOn.class, String.class);

        {
            String[] authors = process(mainType, new String[0], Author.class, String[].class);
            switch(authors.length) {
                case 0: break;
                case 1: yml.put("author", authors[0]); break;
                default: yml.put("authors", authors); break;
            }
        }

        processAndPut(yml, "website", mainType, null, Website.class, String.class);

        if(mainType.getAnnotation(UsesDatabase.class) != null) yml.put("database", true);

        processAndPut(yml, "depend", mainType, null, DependsOn.class, String[].class);

        processAndPut(yml, "softdepend", mainType, null, SoftDependsOn.class, String[].class);

        processAndPut(yml, "prefix", mainType, null, LogPrefix.class, String.class);

        processAndPut(yml, "loadbefore", mainType, null, LoadBefore.class, String[].class);

        Commands.Cmd[] commands = process(mainType, new Commands.Cmd[0], Commands.class, Commands.Cmd[].class);

        Map<String, Object> commandMap = new HashMap<String, Object>();

        for(Commands.Cmd cmd : commands) {
            String name = cmd.value();
            Map<String, Object> desc = new HashMap<String, Object>();
            if(!cmd.desc().isEmpty()) desc.put("description", cmd.desc());
            if(cmd.aliases().length != 0) desc.put("aliases", cmd.aliases());
            if(!cmd.permission().isEmpty()) desc.put("permission", cmd.permission());
            if(!cmd.permissionMessage().isEmpty()) desc.put("permission-message", cmd.permissionMessage());
            if(!cmd.usage().isEmpty()) desc.put("usage", cmd.usage());
            commandMap.put(name, desc);
        }

        if(!commandMap.isEmpty()) yml.put("commands", commandMap);

        Permissions.Perm[] perms = process(mainType, new Permissions.Perm[0], Permissions.class, Permissions.Perm[].class);

        Map<String, Object> permMap = new HashMap<String, Object>();

        for(Permissions.Perm perm : perms) {
            String name = perm.value();
            Map<String, Object> desc = new HashMap<String, Object>();
            if(!perm.desc().isEmpty()) desc.put("description", perm.desc());
            desc.put("default", perm.defaultValue().toString());
            Map<String, Object> children = new HashMap<String, Object>();
            for(String p : perm.children()) children.put(p, true);
            for(String p : perm.antichildren()) children.put(p, false);
            if(!children.isEmpty()) desc.put("children", children);
            permMap.put(name, desc);
        }

        if(!permMap.isEmpty()) yml.put("permissions", permMap);

        Yaml yaml = new Yaml();

        try {
            FileObject file = this.processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "plugin.yml");
            Writer w = file.openWriter();
            try{
                w.append("# Auto-generated plugin.yml, generated at ").append(dFormat.format(new Date())).append(" by ").append(this.getClass().getName()).append("\n\n");
                yaml.dump(yml, w);
            } finally {
                w.flush();
                w.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "NOTE: You are using org.bukkit.plugin.java.annotation, an experimental API!");

        return true;
    }

    private void raiseError(String message) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

    private TypeMirror fromClass(Class<?> clazz) {
        return processingEnv.getElementUtils().getTypeElement(clazz.getName()).asType();
    }

    private <A extends Annotation, R> R processAndPut(
            Map<String, Object> map, String name, Element el, R defaultVal, Class<A> annotationType, Class<R> returnType) {
        R result = process(el, defaultVal, annotationType, returnType);
        if(result != null)
            map.put(name, result);
        return result;
    }
    private <A extends Annotation, R> R process(Element el, R defaultVal, Class<A> annotationType, Class<R> returnType) {
        R result;
        A ann = el.getAnnotation(annotationType);
        if(ann == null) result = defaultVal;
        else {
            try {
                Method value = annotationType.getMethod("value");
                Object res = value.invoke(ann);
                result = (R) (returnType == String.class ? res.toString() : returnType.cast(res));
            } catch (Exception e) {
                throw new RuntimeException(e); // shouldn't happen in theory
            }
        }
        return result;
    }
}
