package org.bukkit.plugin.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *  Part of the plugin annotations framework.
 *  <p>
 *  Represents a list of this plugin's registered commands.
 */

@Target(ElementType.TYPE)
public @interface Commands { // TODO: in java 8, make repeatable.

    public Cmd[] value();

    @Target({})
    public static @interface Cmd {

        /**
         * This command's name.
         */
        public String value();

        /**
         * This command's description.
         */

        public String desc() default "";

        /**
         * This command's aliases.
         */
        public String[] aliases() default {};

        /**
         * This command's permission node.
         */
        public String permission() default "";

        /**
         * This command's permission-check-fail message.
         */
        public String permissionMessage() default "";

        /**
         * This command's usage message.
         */
        public String usage() default "";
    }

}
