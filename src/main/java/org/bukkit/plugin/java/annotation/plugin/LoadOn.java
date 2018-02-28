package org.bukkit.plugin.java.annotation.plugin;

import org.bukkit.plugin.PluginLoadOrder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents the optional load order of the plugin.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface LoadOn {
    PluginLoadOrder loadOn();
}
