package org.bukkit.plugin.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents the plugin's hard dependencies.
 */

@Target(ElementType.TYPE)
public @interface DependsOn {

    public String[] value();

}
