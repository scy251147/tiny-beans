package org.tiny.beans.test;

import org.junit.jupiter.api.Test;
import org.tiny.beans.core.BeanContext;
import org.tiny.beans.test.service.UserService;

public class BeanTest {

    @Test
    public void test(){

        BeanContext beanContext = new BeanContext(BeanConfig.class);

        UserService userService = (UserService) beanContext.getBean("userService");

        System.out.println(userService);

    }

}
