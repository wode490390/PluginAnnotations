# Usage
Add this jar to your pom.xml to enable automatic annotation-based plugin.yml generation.

The only *required* annotation is the ```@Plugin``` annotation. All other annotations are optional.
See the [wiki](https://www.spigotmc.org/wiki/plugin-yml/) for more information.

## Example Usage
```
@Plugin(name = "TestPlugin", version = "1.0")
@Description("A test plugin")
@LoadOrder(PluginLoadOrder.STARTUP)
@Author("md_5")
@Website("www.spigotmc.org")
@LogPrefix("Testing")
@Dependency("WorldEdit")
@Dependency("Towny")
@LoadBefore("Towny")
@SoftDependency("EssentialsX")
@Commands(@Command(name = "foo", desc = "Foo command", aliases = {"foobar", "fubar"}, permission = "test.foo", permissionMessage = "You do not have permission!", usage = "/<command> [test|stop]"))
@Permission(name = "test.foo", desc = "Allows foo command", defaultValue = PermissionDefault.OP)
@Permission(name = "test.*", desc = "Wildcard permission", defaultValue = PermissionDefault.OP, children = {@ChildPermission(name ="test.foo")})
@ApiVersion(ApiVersion.Target.v1_13)
public class TestPlugin extends JavaPlugin {
```
Output:

```
# Auto-generated plugin.yml, generated at 2018/07/12 22:16:27 by org.bukkit.plugin.java.annotation.PluginAnnotationProcessor

# Auto-generated plugin.yml, generated at 2018/07/13 00:16:24 by org.bukkit.plugin.java.annotation.PluginAnnotationProcessor

main: org.spigotmc.spigot.TestPlugin
name: TestPlugin
version: '1.0'
description: A test plugin
load: STARTUP
author: md_5
website: www.spigotmc.org
prefix: Testing
depend:
- WorldEdit
- Towny
softdepend:
- EssentialsX
loadbefore:
- Towny
commands:
  TestCommand:
    aliases: testext2
    permission: test.testext
    permission-message: Oopsy!
    usage: /testext test test
permissions:
  test.foo:
    description: Allows foo command
  test.*:
    description: Wildcard permission
    children:
      test.foo: true
api-version: '1.13'
```

As of version 1.2.0-SNAPSHOT you can now also use the ```@Command``` and ```@Permission```
annotations on classes that implement CommandExecutor.

For example:
```
@Command(name = "TestCommand", aliases = "testext2", permission = "test.testext", permissionMessage = "Oopsy!", usage = "/testext test test")
@Permission(name = "test.testext", desc = "Provides access to /textext command", defaultValue = PermissionDefault.TRUE)
public class TestCommand implements CommandExecutor {
```

As of version 1.2.0-SNAPSHOT the ```@ApiVersion``` annotation was introduced to bring compatibility for
Bukkit's new ```api-version``` plugin.yml option. This defaults to ```ApiVersion.Target.DEFAULT``` if not specified or included.
All pre-1.13 plugins MUST use ```ApiVersion.Target.DEFAULT``` in order for the plugin to be loaded correctly.
