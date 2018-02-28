package org.bukkit.plugin.java.annotation.dependency;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents the plugins this plugin should try to load before this plugin will attempt to load.
 *  A plugin will still load if a soft dependency is not present.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface SoftDependsOn {

    SoftDependency[] value() default {};

}
