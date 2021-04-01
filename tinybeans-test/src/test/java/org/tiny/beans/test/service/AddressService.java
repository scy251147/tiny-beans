package org.tiny.beans.test.service;

import org.tiny.beans.sdk.annotation.Bean;
import org.tiny.beans.sdk.func.BeanInit;

/**
 * @author shichaoyang
 * @Description:
 * @date 2021-04-01 15:42
 */
@Bean("addressService")
public class AddressService implements BeanInit {

    @Override
    public void afterPropertiesSet() {
        System.out.println(" address service init method biz running ");
    }
}
