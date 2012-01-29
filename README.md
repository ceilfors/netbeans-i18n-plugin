# Netbeans I18n Plugin
Provides basic functionality to support i18n in NetBeans

## Installation
> Supports only __NetBeans 7.1__ onwards
1. __Download__ the [plugin](https://github.com/downloads/ceilfors/netbeans-i18n-plugin/netbeans-i18n-plugin-1.0.nbm).
1. Open NetBeans IDE and go to Tools > Plugins.
1. Select tab Downloaded, click __Add plugins__. Select the downloaded nbm file.
1. Click Install and follow the instructions.
1. Restart NetBeans. Some features will only work after restarting.

## Future `welcome contributors :)`
* Hint fix that will automatically extracts string literals to a specified Bundle.properties
* Configuration of the Suppression string from NetBeans Option windows
* To fix the issue when there are more than 1 string literal found in a line. More than 1 hint will be displayed a the moment.

## Features
### Hint fix  
The only fix that is supported now is to suppress the String literal found in the editor. This this fix is selected, a java line comment would be added to the end of the line. The usage is like any other NetBeans hint:  
![show_hint.jpg](https://github.com/ceilfors/netbeans-i18n-plugin/raw/master/wiki/show_hint.jpg)  
  
When the fix is selected, the suppression comment will be added:  
![suppression_applied.jpg](https://github.com/ceilfors/netbeans-i18n-plugin/raw/master/wiki/suppression_applied.jpg)

### Task  
![task.jpg](https://github.com/ceilfors/netbeans-i18n-plugin/raw/master/wiki/task.jpg)  
Displays a Warning when a String literal not i18n-ed in the NetBeans Task List window. When the plugin is installed for the first time, the Task's Type (I18n Tasks) will be unchecked by default. It needs to be enabled in the Task List Filter window:  
![task_list.jpg](https://github.com/ceilfors/netbeans-i18n-plugin/raw/master/wiki/task_list.jpg)  
This feature is using [javaparser](http://code.google.com/p/javaparser/) to parse the java file. When this Task's Type is finally enabled, NetBeans will scan all of the files within the scope stated and this plugin will parse all of the java files found manually. This is due to NetBeans API limitation. When lot of "Task" plugins are already installed, this plugin maybe have a performance impact as well. If skeptical, disable this feature from the Task filter and use the Netbeans' Inspect and Transform feature.