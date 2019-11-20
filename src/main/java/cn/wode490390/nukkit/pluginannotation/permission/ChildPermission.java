package cn.wode490390.nukkit.pluginannotation.permission;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a child permission for a {@link Permission}
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChildPermission {
    /**
     * The name of the child permission.
     */
    String name();

    /**
     * The description of the child permission.
     */
    String desc() default "";

    /**
     * The default {@link cn.nukkit.permission.Permission} of the child permission.
     */
    String defaultValue() default cn.nukkit.permission.Permission.DEFAULT_PERMISSION;
}
