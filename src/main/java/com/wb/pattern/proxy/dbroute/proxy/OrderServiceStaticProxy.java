package com.wb.pattern.proxy.dbroute.proxy;

import com.wb.pattern.proxy.dbroute.IOrderService;
import com.wb.pattern.proxy.dbroute.Order;
import com.wb.pattern.proxy.dbroute.db.DynamicDataSourceEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tom on 2019/3/10.
 */
public class OrderServiceStaticProxy implements IOrderService {
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    private IOrderService orderService;
    public OrderServiceStaticProxy(IOrderService orderService) {
        this.orderService = orderService;
    }

    public int createOrder(Order order) {
        before();
        Long time = order.getCreateTime();
        Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
        System.out.println("静态代理类自动分配到【DB_" +  dbRouter + "】数据源处理数据" );
        DynamicDataSourceEntity.set(dbRouter);

        this.orderService.createOrder(order);
        DynamicDataSourceEntity.restore();
        after();
        return 0;
    }

    public void before() {
        System.out.println("this is before...");
    }

    public void after() {
        System.out.println("this is after...");
    }

}
