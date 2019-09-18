package com.wb.pattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Auther: wb_cheng
 * @Date: 2019/9/17 17:36
 * @Description:
 */
public class WBMeiPo implements WBInvocationHandler {

    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    private void after() {
        System.out.println("感觉不错就准备办事");
    }

    private void before() {
        System.out.println("我是媒婆，开始物色");
    }

    public Object getInstance(Object target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return WBProxy.newProxyInstance(new WBClassLoader(), clazz.getInterfaces(), this);
    }
}
