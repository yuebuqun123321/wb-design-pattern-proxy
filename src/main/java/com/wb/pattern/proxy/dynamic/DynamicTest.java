package com.wb.pattern.proxy.dynamic;

import com.wb.pattern.proxy.Person;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @Auther: wb_cheng
 * @Date: 2019/9/17 17:41
 * @Description:
 */
public class DynamicTest {

    public static void main(String[] args) {
        Person person = new Customer();
        Person proxy = (Person) new MeiPo().getInstance(person);
        proxy.findLove();
        System.out.println(proxy);

        //生成代理类字节码
        try {
            byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
            FileOutputStream fos = new FileOutputStream("C:\\Users\\ymm\\Desktop\\$Proxy0.class");
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
