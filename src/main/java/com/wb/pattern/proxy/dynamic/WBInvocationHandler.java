package com.wb.pattern.proxy.dynamic;

import java.lang.reflect.Method;

/**
 * @Auther: wb_cheng
 * @Date: 2019/9/17 21:37
 * @Description:
 */
public interface WBInvocationHandler {

    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

}
