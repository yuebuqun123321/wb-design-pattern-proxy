package com.wb.pattern.proxy.staticproxy;

import com.wb.pattern.proxy.Person;

/**
 * @Auther: wb_cheng
 * @Date: 2019/9/17 16:41
 * @Description:
 */
public class Father implements Person {

    private Son son;

    public Father(Son son) {
        this.son = son;
    }

    @Override
    public void findLove() {
        System.out.println("父亲开始帮儿子找对象");
        this.son.findLove();
        System.out.println("比较满意，开始处对象啦");
    }
}
