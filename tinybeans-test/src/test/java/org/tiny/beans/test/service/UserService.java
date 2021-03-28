package org.tiny.beans.test.service;

import org.tiny.beans.sdk.Inject;
import org.tiny.beans.sdk.BeanScan;
import org.tiny.beans.sdk.BeanInit;
import org.tiny.beans.test.model.User;

@BeanScan("userService")
public class UserService implements BeanInit {

    @Inject
    private User user;

    private int serviceCount = 0;

    @Override
    public void afterPropertiesSet() {

    }

    public void print(){
        System.out.println(user);
    }


}
