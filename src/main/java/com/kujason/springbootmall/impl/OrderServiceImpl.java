package com.kujason.springbootmall.impl;

import com.kujason.springbootmall.dao.OrderDao;
import com.kujason.springbootmall.dao.ProductDao;
import com.kujason.springbootmall.dto.BuyItem;
import com.kujason.springbootmall.dto.CreateOrderRequest;
import com.kujason.springbootmall.model.Order;
import com.kujason.springbootmall.model.OrderItem;
import com.kujason.springbootmall.model.Product;
import com.kujason.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional //  修改多張表單時必加上
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
         int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

         for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
             Product product = productDao.getProductById(buyItem.getProductId());

             //  計算總價錢
             int amount = buyItem.getQuantity() * product.getPrice();
             totalAmount = totalAmount + amount;

             //  轉換 BuyItem to OrderItem
             OrderItem orderItem = new OrderItem();
             orderItem.setProductId(buyItem.getProductId());
             orderItem.setQuantitiy(buyItem.getQuantity());
             orderItem.setAmount(amount);

             orderItemList.add(orderItem);
         }

       //  創建訂單
       Integer orderId = orderDao.createOrder(userId,totalAmount);

       //  另外也創建 OrderItem 資料
       orderDao.createOrderItems(orderId,orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

       return order;
    }
}
