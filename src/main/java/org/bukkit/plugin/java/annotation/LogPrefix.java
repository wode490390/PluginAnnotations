package org.bukkit.plugin.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents the prefix used for the plugin's log entries, defaults to plugin name.
 */

@Target(ElementType.TYPE)
public @interface LogPrefix {

    public String value();
}
