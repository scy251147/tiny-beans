# tiny-beans

### Yet another java bean container.

It's tiny, no need to import other dependencies, jar size totally less than 30kb.

It's easy to use, Spring like mechanism can save your time. 


### Bean Scan and config:

First of first, we need a bean scan config class to locate the package and config file:

```
package org.tiny.beans.test;

import org.tiny.beans.sdk.annotation.BeanScan;

@BeanScan(packagePath = "org.tiny.beans.test", packageConfig = "config.properties")
    public class BeanConfig {
}
```

From above code sample we can see that the defined package scan path is org.tiny.beans.test, while the config file is config.properties.
These two configs will enable us to search the classes and config files. If in your project, there is no config file needed, we can ignore the packageConfig propertity.

### Bean setting:

When we want to create the instance of beans, we can use the @Bean annotation along with the specified bean name. When tomcat or other container startup, it will search the beans by this annotation and create the instance.
If we want prototype beans, we can add @Scope along with ScopeType.Prototype to go, while singleton beans goes by default.

```
@Bean("userService")
@Scope(ScopeType.Prototype)
public class UserService implements BeanInit {

    @Inject
    private User user;

    @Value(value = "user.count")
    private int serviceCount = 0;

    @Value(value = "test.boolean")
    private boolean testBoolean = false;

    @Override
    public void afterPropertiesSet() {
        System.out.println(" user service init method biz running ");
    }

    public User print() {
        user.setAge(33);
        user.setName("cxsr");
        System.out.println(user.getName() + "||" + serviceCount + "||" + testBoolean);
        return user;
    }
}
```

From above code, we can get the usage. 
@Inject to inject the bean instance to another bean; 
@Value to inject the config values;
BeanInit interface to perform the afterPropertiesSet function;
BeanPost interface to perform the postProcessBeforeInitialization and postProcessAfterInitialization functionality, 
but the most powerful skill for this interface is perform dynamic proxy, code below:

```
package org.tiny.beans.test.service;

import org.tiny.beans.sdk.annotation.Bean;
import org.tiny.beans.sdk.func.BeanPost;


  @Bean("userPostProcessService")
  public class UserPostProcessService implements BeanPost {
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
  System.out.println("初始化前，对象是原来的bean"+bean);
  return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
  // o = new Object();
  //System.out.println("初始化后，对象是新的另外的bean，动态代理过了" + o);
  //return o;
  return bean;
  }
  }

```

Detailed usage lied in tinybeans-test module.

Any advice will be appreciated.