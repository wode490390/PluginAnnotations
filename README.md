# plugin-annotations
Add this jar to your pom.xml to enable automatic annotation-based plugin.yml generation.

## Example Usage
```
package org.spigotmc.annotationtest;

import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.*;
import org.bukkit.plugin.java.annotation.*;
import org.bukkit.plugin.java.annotation.Commands.Cmd;
import org.bukkit.plugin.java.annotation.Permissions.Perm;

@Main
@Name("Test")
@Version("v1.0")
@Description("A test plugin.")
@LoadOn(PluginLoadOrder.POSTWORLD)
@Author("md_5")
@Website("spigotmc.org")
@UsesDatabase
@DependsOn({"WorldEdit", "Towny"})
@SoftDependsOn("Vault")
@LogPrefix("Testing")
@LoadBefore("Essentials")
@Commands({
        @Cmd(
                value = "foo",
                desc = "Foo command",
                aliases = {"foobar", "fubar"},
                permission = "test.foo",
                permissionMessage = "You do not have permission!",
                usage = "/<command> [test|stop]"
        ),
        @Cmd("bar")
})
@Permissions({
        @Perm(
                value = "test.foo",
                desc = "Allows foo command",
                defaultValue = PermissionDefault.OP
        ),
        @Perm(
                value = "test.*",
                desc = "Wildcard perm",
                defaultValue = PermissionDefault.OP,
                children = {"test.foo"}
        )
})
public class Test extends JavaPlugin {}
```
Output:

```
# Auto-generated plugin.yml, generated at 2015/02/20 20:06:29 by org.bukkit.plugin.java.annotation.PluginAnnotationProcessor

website: spigotmc.org
depend: [WorldEdit, Towny]
commands:
  foo:
    description: Foo command
    usage: /<command> [test|stop]
    permission: test.foo
    permission-message: You do not have permission!
    aliases: [foobar, fubar]
  bar: {}
database: true
main: org.spigotmc.annotationtest.Test
version: v1.0
softdepend: [Vault]
author: md_5
description: A test plugin.
name: Test
prefix: Testing
permissions:
  test.*:
    default: op
    description: Wildcard perm
    children: {test.foo: true}
  test.foo: {default: op, description: Allows foo command}
load: POSTWORLD
loadbefore: [Essential:s]
```