package org.bukkit.plugin.java.annotation.plugin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Plugin {
    /**
     * The name of this plugin
     */
    String name();

    /**
     * This version of this plugin
     */
    String version();

    String DEFAULT_VERSION = "v0.0";
}
