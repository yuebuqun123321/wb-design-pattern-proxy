package com.wb.pattern.proxy.cglib;

import com.wb.pattern.proxy.Person;
import com.wb.pattern.proxy.dynamic.Customer;

/**
 * @Auther: wb_cheng
 * @Date: 2019/9/17 22:58
 * @Description:
 */
public class CGlibTest {

    public static void main(String[] args) {
        Person instance = (Person) new CGlibMeipo().getInstance(Customer.class);
        instance.findLove();
    }

}
