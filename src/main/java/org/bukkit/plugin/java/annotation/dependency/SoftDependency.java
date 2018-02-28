package org.bukkit.plugin.java.annotation.dependency;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Represents a soft (optional) dependency for this plugin.
 * If this dependency is not present, the plugin will still load.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Repeatable(SoftDependsOn.class)
public @interface SoftDependency {
    /**
     * A plugin that is required in order for this plugin to have full functionality.
     */
    String plugin();
}
