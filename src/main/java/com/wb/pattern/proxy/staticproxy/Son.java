package com.wb.pattern.proxy.staticproxy;

import com.wb.pattern.proxy.Person;

/**
 * @Auther: wb_cheng
 * @Date: 2019/9/17 16:41
 * @Description:
 */
public class Son implements Person {
    @Override
    public void findLove() {
        System.out.println("儿子喜欢白富美");
    }
}
