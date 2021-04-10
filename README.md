# tiny-beans

### Yet another java bean container.

It's tiny, no need to import other dependencies, jar size totally less than 30kb.

It's easy to use, Spring like mechanism can save your time. 


### Bean Scan config:

First of first, we need a bean scan config class to locate the package and config file:

``
package org.tiny.beans.test;

import org.tiny.beans.sdk.annotation.BeanScan;

@BeanScan(packagePath = "org.tiny.beans.test", packageConfig = "config.properties")
    public class BeanConfig {
}
``
