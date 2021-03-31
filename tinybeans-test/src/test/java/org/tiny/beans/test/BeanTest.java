package org.tiny.beans.test;

import org.junit.jupiter.api.Test;
import org.tiny.beans.core.BeanManager;
import org.tiny.beans.core.bak.BeanContexter;
import org.tiny.beans.test.service.UserService;

public class BeanTest {

    @Test
    public void test(){

        BeanManager beanManager = new BeanManager(BeanConfig.class);

        UserService userService = (UserService) beanManager.getBean("userService");

        //null pinter exception, need check path

        System.out.println(userService);

    }

}
