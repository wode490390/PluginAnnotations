package org.bukkit.plugin.java.annotation.permission;

import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.plugin.author.Authors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a plugin permission
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Repeatable(Permissions.class)
public @interface Permission {
    /**
     * This perm's name.
     */
    String name();

    /**
     * This perm's description.
     */
    String desc() default "";

    /**
     * This perm's default {@link PermissionDefault}
     */
    PermissionDefault defaultValue() default PermissionDefault.OP;

    /**
     * This permission's child nodes ({@link ChildPermission})
     */
    ChildPermission[] children() default {};
}
