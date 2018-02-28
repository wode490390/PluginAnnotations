package org.bukkit.plugin.java.annotation.plugin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Denotes this plugin as using Bukkit's bundled database system.
 *  @deprecated Bukkit no longer supports database(s) in the plugin.yml
 */

@Deprecated
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface UsesDatabase {}
