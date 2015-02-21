package org.bukkit.plugin.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents the plugins this plugin should be loaded before
 */

@Target(ElementType.TYPE)
public @interface LoadBefore {

    public String[] value();

}
