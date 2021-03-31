package org.tiny.beans.test;

import org.junit.jupiter.api.Test;
import org.tiny.beans.core.bak.BeanContexter;
import org.tiny.beans.test.service.UserService;

public class BeanTest {

    @Test
    public void test(){

        BeanContexter beanContext = new BeanContexter(BeanConfig.class);

        UserService userService = (UserService) beanContext.getBean("userService");

        //null pinter exception, need check path

        System.out.println(userService);

    }

}
