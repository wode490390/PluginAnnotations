package org.bukkit.plugin.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents the name of the plugin.
 *  <p>
 *  If not present in a class annotated with {@link Main} the name defaults to Class.getSimpleName() and will emmit a warning.
 */

@Target(ElementType.TYPE)
public @interface Name {

    public String value();
}
