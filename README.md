# Plugin Annotations for Nukkit

## Usage
Add this jar to your pom.xml to enable automatic annotation-based plugin.yml generation.

The only *required* annotation is the ```@Plugin``` annotation. All other annotations are optional.
See the [wiki](https://github.com/wode490390/PluginAnnotations/wiki) for more information.

### [Example Usage](https://github.com/wode490390/PluginAnnotations-Example)
```java
@Plugin(name = "TestPlugin", version = "1.0")
@Description("A test plugin")
@LoadOrder(PluginLoadOrder.STARTUP)
@Author("md_5")
@Website("www.spigotmc.org")
@LogPrefix("Testing")
@Dependency("FastAsyncWorldEdit")
@Dependency("LuckPerms")
@LoadBefore("MobPlugin")
@SoftDependency("PlotSquared")
@Commands(@Command(name = "foo", desc = "Foo command", aliases = {"foobar", "fubar"}, permission = "test.foo", permissionMessage = "You do not have permission!", usage = "/<command> [test|stop]"))
@Permission(name = "test.foo", desc = "Allows foo command", defaultValue = "op")
@Permission(name = "test.*", desc = "Wildcard permission", defaultValue = "op", children = {@ChildPermission(name ="test.foo")})
@ApiVersion("1.0.0")
public class TestPlugin extends PluginBase {
```
Output:

```yaml
# Auto-generated plugin.yml, generated at 2018/07/12 22:16:27 by cn.wode490390.nukkit.pluginannotation.PluginAnnotationProcessor

main: io.nukkit.exampleplugin.ExamplePlugin
name: TestPlugin
version: '1.0'
description: A test plugin
load: STARTUP
author: md_5
website: www.spigotmc.org
prefix: Testing
depend:
- FastAsyncWorldEdit
- LuckPerms
softdepend:
- PlotSquared
loadbefore:
- MobPlugin
commands:
  foo:
    aliases:
    - foobar
    - fubar
    description: Foo command
    permission: test.foo
    permission-message: You do not have permission!
    usage: /<command> [test|stop]
permissions:
  test.foo:
    description: Allows foo command
  test.*:
    description: Wildcard permission
    children:
      test.foo: {}
api:
- 1.0.0
```

As of version 1.2.0-SNAPSHOT you can now also use the ```@Commands``` and ```@Permission```
annotations on classes that implement CommandExecutor.

For example:
```java
@Commands(@Command(name = "TestCommand", aliases = "testext2", permission = "test.testext", permissionMessage = "Oopsy!", usage = "/testext test test"))
@Permission(name = "test.testext", desc = "Provides access to /textext command", defaultValue = PermissionDefault.TRUE)
public class TestCommand implements CommandExecutor {
```
