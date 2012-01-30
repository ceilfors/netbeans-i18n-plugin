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
### Hint
A hint will be shown in the editor when there is a String literal found. This hint is placed under the _General_ category with _"String is not i18n-ed"_ as its name. The only hint fix that is supported now is to suppress them. Like any other NetBeans hint, the fix will be shown when the hint icon is clicked:  
![show_hint.jpg](https://github.com/ceilfors/netbeans-i18n-plugin/raw/master/wiki/show_hint.jpg)  
  
When the fix is selected, a suppression comment will be added to the end of the line:  
![suppression_applied.jpg](https://github.com/ceilfors/netbeans-i18n-plugin/raw/master/wiki/suppression_applied.jpg)

### Task  
![task.jpg](https://github.com/ceilfors/netbeans-i18n-plugin/raw/master/wiki/task.jpg)  
Displays a Warning when a String literal is not i18n-ed in the NetBeans Task List window. When the plugin is installed for the first time, the Task's Type (I18n Tasks) will be unchecked by default. It needs to be enabled in the Task List Filter window:  
![task_list.jpg](https://github.com/ceilfors/netbeans-i18n-plugin/raw/master/wiki/task_list.jpg)  
This feature is using [javaparser](http://code.google.com/p/javaparser/) to parse the java file. When this Task's Type is enabled, NetBeans will scan all of the files within the scope stated and this plugin will parse all of the java files found manually, hence this plugin might cause a performance degradation. This have to be done due to NetBeans API limitation. If skeptical, disable this Task feature from the Task filter and use the Netbeans' Inspect and Transform feature. In the Inspect and Transform window, select _Single inspection_ and choose _"String is not i18n-ed"_ under the _General_ category.