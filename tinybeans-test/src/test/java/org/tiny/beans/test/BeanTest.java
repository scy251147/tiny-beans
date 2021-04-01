package org.tiny.beans.test;

import org.junit.jupiter.api.Test;
import org.tiny.beans.core.BeanManager;
import org.tiny.beans.test.model.User;
import org.tiny.beans.test.service.UserPostProcessService;
import org.tiny.beans.test.service.UserService;

public class BeanTest {

    @Test
    public void test() {

        BeanManager beanManager = new BeanManager(BeanConfig.class);
        System.out.println(beanManager);

        UserService userService = (UserService) beanManager.getBean("userService");
        System.out.println(userService);
        System.out.println(userService.print());

        UserPostProcessService userPostProcessService = (UserPostProcessService) beanManager.getBean("userPostProcessService");
        System.out.println(userPostProcessService);

        User user = (User) beanManager.getBean("user");
        System.out.println(user);

    }

}
