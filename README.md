# Usage
Add this jar to your pom.xml to enable automatic annotation-based plugin.yml generation.

The only *required* annotation is the ```@Plugin``` annotation. All other annotations are optional.
See the [wiki](https://www.spigotmc.org/wiki/plugin-yml/) for more information.

## Example Usage
```
package org.spigotmc.annotationtest;

import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.*;
import org.bukkit.plugin.java.annotation.*;
import org.bukkit.plugin.java.annotation.Commands.Cmd;
import org.bukkit.plugin.java.annotation.Permissions.Perm;

@Plugin(name = "TestPlugin", version = "1.0")
@Description(desc = "A test plugin")
@LoadOn(loadOn = PluginLoadOrder.POSTWORLD) // defaults to PluginLoadOrder.POSTWORLD if not preset
@Author(name = "md_5")
@Website(url = "spigotmc.org")
@LogPrefix(prefix = "Testing")
@Dependency(plugin = "WorldEdit")
@Dependency(plugin = "Towny")
@LoadBefore(plugin = "Essentials")
@SoftDependency(plugin = "FAWE")
@Command(name = "foo", desc = "Foo command", aliases = {"foobar", "fubar"}, permission = "test.foo", permissionMessage = "You do not have permission!", usage = "/<command> [test|stop]")
@Permission(name = "test.foo", desc = "Allows foo command", defaultValue = PermissionDefault.OP)
@Permission(name = "test.*", desc = "Wildcard permission", defaultValue = PermissionDefault.OP, children = {@ChildPermission(name ="test.foo")})
public class Test extends JavaPlugin {}
```
Output:

```
# Auto-generated plugin.yml, generated at 2018/03/06 18:15:44 by org.bukkit.plugin.java.annotation.PluginAnnotationProcessor

main: org.spigotmc.annotationtest.Test
name: TestPlugin
version: '1.0'
description: A test plugin
load: POSTWORLD
author: md_5
website: spigotmc.org
prefix: Testing
depend:
- WorldEdit
- Towny
softdepend:
- FAWE
loadbefore:
- Essentials
commands:
  foo:
    description: Foo command
    aliases:
    - foobar
    - fubar
    permission: test.foo
    permission-message: You do not have permission!
    usage: /<command> [test|stop]
permissions:
  test.foo:
    description: Allows foo command
    default: op
  test.*:
    description: Wildcard permission
    default: op
    children:
      test.foo: true
```
