package org.tiny.beans.test.service;

import org.tiny.beans.sdk.annotation.Bean;
import org.tiny.beans.sdk.annotation.Inject;
import org.tiny.beans.sdk.annotation.Value;
import org.tiny.beans.sdk.func.BeanInit;
import org.tiny.beans.test.model.User;

@Bean("userService")
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
