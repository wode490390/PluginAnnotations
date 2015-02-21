package org.bukkit.plugin.java.annotation;

import org.bukkit.plugin.PluginLoadOrder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents the optional load order of the plugin.
 */

@Target(ElementType.TYPE)
public @interface LoadOn {

    public PluginLoadOrder value();
}
