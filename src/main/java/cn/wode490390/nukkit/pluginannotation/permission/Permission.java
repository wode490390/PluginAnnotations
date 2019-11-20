package cn.wode490390.nukkit.pluginannotation.permission;

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
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Permissions.class)
public @interface Permission {
    /**
     * This permission's name.
     */
    String name();

    /**
     * This permission's description.
     */
    String desc() default "";

    /**
     * This permission's default {@link cn.nukkit.permission.Permission}
     */
    String defaultValue() default cn.nukkit.permission.Permission.DEFAULT_PERMISSION;

    /**
     * This permission's child nodes ( {@link ChildPermission} )
     */
    ChildPermission[] children() default {};
}
