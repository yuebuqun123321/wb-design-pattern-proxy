package com.wb.pattern.proxy.staticproxy;

import com.wb.pattern.proxy.Person;

/**
 * @Auther: wb_cheng
 * @Date: 2019/9/17 16:43
 * @Description:
 */
public class StaticTest {

    public static void main(String[] args) {
        Son son = new Son();
        Person father = new Father(son);
        father.findLove();
    }

}
