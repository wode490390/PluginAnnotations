package org.bukkit.plugin.java.annotation.plugin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DEPRECATED: Use {@link Plugin} instead.
 * Marks this class (which <i>must</i> subclass JavaPlugin) as this plugin's main class.
 * <p>
 * This class is part of the plugin annotation framework that automates plugin.yml.
 * <p>
 * Example:
 * <pre>
 * <code>{@literal @}Main
 * {@literal @}Name("Test")
 * {@literal @}Version("v1.0")
 * {@literal @}Description("A test plugin.")
 * {@literal @}LoadOn(PluginLoadOrder.POSTWORLD)
 * {@literal @}Author("md_5")
 * {@literal @}Website("spigotmc.org")
 * {@literal @}UsesDatabase
 * {@literal @}DependsOn({"WorldEdit", "Towny"})
 * {@literal @}SoftDependsOn("Vault")
 * {@literal @}LogPrefix("Testing")
 * {@literal @}LoadBefore("Essentials")
 * {@literal @}Commands({
 *     {@literal @}Command(
 *         name = "foo",
 *         name = "Foo command",
 *         aliases = {"foobar", "fubar"},
 *         permission = "test.foo",
 *         permissionMessage = "You do not have permission!",
 *         usage = "/<command> [test|stop]"
 *     ),
 *     {@literal @}Command("bar")
 * })
 * {@literal @}Permissions({
 *     {@literal @}Perm(
 *         name = "test.foo",
 *         name = "Allows foo command",
 *         defaultValue = PermissionDefault.OP,
 *     ),
 *     {@literal @}Perm(
 *         name = "test.*",
 *         name = "Wildcard perm",
 *         defaultValue = PermissionDefault.OP,
 *         children = {"test.foo"}
 *     )
 * })
 * public class Test extends JavaPlugin { ... }
 * </code>
 * </pre>
 * @deprecated use {@link Plugin} instead.
 */
@Deprecated
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Main {}
