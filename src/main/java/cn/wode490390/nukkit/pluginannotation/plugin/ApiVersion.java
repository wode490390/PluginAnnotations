package cn.wode490390.nukkit.pluginannotation.plugin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a api version
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( ElementType.TYPE )
@Repeatable(ApiVersions.class)
public @interface ApiVersion {
    /**
     * The Nukkit API version this plugin supports.
     */
    String value();
}
