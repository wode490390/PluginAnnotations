package org.bukkit.plugin.java.annotation;

import org.bukkit.permissions.PermissionDefault;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents a list of this plugin's registered permissions.
 */

@Target(ElementType.TYPE)
public @interface Permissions { // TODO: in java 8, make repeatable.

    public Perm[] value();

    @Target({})
    public static @interface Perm {

        /**
         * This perm's name.
         */
        public String value();

        /**
         * This perm's description.
         */

        public String desc() default "";

        /**
         * This perm's default.
         */
        public PermissionDefault defaultValue() default PermissionDefault.OP;

        /**
         * This perm's child nodes
         */
        public String[] children() default {};

        /**
         * This perms's negated child nodes
         */
        public String[] antichildren() default {};

    }

}
