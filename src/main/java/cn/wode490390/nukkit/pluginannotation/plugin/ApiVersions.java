package cn.wode490390.nukkit.pluginannotation.plugin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a list of api version(s) for this plugin.
 * <br>
 * This specific annotation should not be used by people who do not know
 * how repeating annotations work.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApiVersions {
    ApiVersion[] value() default {};
}
