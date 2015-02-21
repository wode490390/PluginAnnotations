package org.bukkit.plugin.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
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
 *     {@literal @}Cmd(
 *         value = "foo",
 *         desc = "Foo command",
 *         aliases = {"foobar", "fubar"},
 *         permission = "test.foo",
 *         permissionMessage = "You do not have permission!",
 *         usage = "/<command> [test|stop]"
 *     ),
 *     {@literal @}Cmd("bar")
 * })
 * {@literal @}Permissions({
 *     {@literal @}Perm(
 *         value = "test.foo",
 *         desc = "Allows foo command",
 *         defaultValue = PermissionDefault.OP,
 *     ),
 *     {@literal @}Perm(
 *         value = "test.*",
 *         desc = "Wildcard perm",
 *         defaultValue = PermissionDefault.OP,
 *         children = {"test.foo"}
 *     )
 * })
 * public class Test extends JavaPlugin { ... }
 * </code>
 * </pre>
 */

@Target(ElementType.TYPE)
public @interface Main {}
