package com.wb.pattern.proxy.dynamic;

import com.wb.pattern.proxy.Person;

/**
 * @Auther: wb_cheng
 * @Date: 2019/9/17 17:41
 * @Description:
 */
public class Customer implements Person {
    @Override
    public void findLove() {
        System.out.println("目标：高富帅");
        System.out.println("身高：180");
        System.out.println("money：1000W");
    }
}
