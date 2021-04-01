package org.tiny.beans.test.service;

import org.tiny.beans.sdk.annotation.Bean;
import org.tiny.beans.sdk.annotation.Inject;
import org.tiny.beans.sdk.annotation.BeanScan;
import org.tiny.beans.sdk.func.BeanInit;
import org.tiny.beans.test.model.User;

@Bean("userService")
public class UserService implements BeanInit {

    @Inject
    private User user;

    private int serviceCount = 0;

    @Override
    public void afterPropertiesSet() {

    }

    public User print(){
         user.setAge(33);
         user.setName("cxsr");
         System.out.println(user.getName());
         return user;
    }


}
