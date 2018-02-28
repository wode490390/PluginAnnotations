package org.bukkit.plugin.java.annotation.plugin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents the version of the plugin.
 *  <p>
 *  If not present in a class annotated with {@link Main} the name defaults to "v0.0" and will emmit a warning.
 *  @deprecated use {@link Plugin#version()} instead
 */

@Deprecated
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Version {
    String version();

    String DEFAULT_VERSION = "v0.0";
}
