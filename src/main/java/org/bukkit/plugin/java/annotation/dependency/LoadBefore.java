package org.bukkit.plugin.java.annotation.dependency;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents the plugin this plugin should be loaded before
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Repeatable(LoadBeforePlugins.class)
public @interface LoadBefore {
    /**
     * A plugin that should be loaded after your plugin
     */
    String plugin();
}
