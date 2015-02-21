package org.bukkit.plugin.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents the version of the plugin.
 *  <p>
 *  If not present in a class annotated with {@link Main} the name defaults to "v0.0" and will emmit a warning.
 */

@Target(ElementType.TYPE)
public @interface Version {

    public String value();

    public static final String DEFAULT_VERSION = "v0.0";
}
