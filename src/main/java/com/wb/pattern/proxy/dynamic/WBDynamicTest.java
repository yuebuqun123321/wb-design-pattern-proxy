package com.wb.pattern.proxy.dynamic;

import com.wb.pattern.proxy.Person;

/**
 * @Auther: wb_cheng
 * @Date: 2019/9/17 22:31
 * @Description:
 */
public class WBDynamicTest {

    public static void main(String[] args) {
        Person person = new Customer();
        Person proxy = (Person) new WBMeiPo().getInstance(person);
        proxy.findLove();
    }

}
