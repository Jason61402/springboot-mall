package com.kujason.springbootmall.impl;

import com.kujason.springbootmall.dao.OrderDao;
import com.kujason.springbootmall.dao.ProductDao;
import com.kujason.springbootmall.dao.UserDao;
import com.kujason.springbootmall.dto.BuyItem;
import com.kujason.springbootmall.dto.CreateOrderRequest;
import com.kujason.springbootmall.model.Order;
import com.kujason.springbootmall.model.OrderItem;
import com.kujason.springbootmall.model.Product;
import com.kujason.springbootmall.model.User;
import com.kujason.springbootmall.service.OrderService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log =LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional //  修改多張表單時必加上
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
//      檢查 user 是否存在
        User user = userDao.getUserById(userId);

        if(user == null){
            log.warn("該 userId 不存在",userId);
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

         for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
             Product product = productDao.getProductById(buyItem.getProductId());

             if(product == null){
                 log.warn("商品 {} 不存在",buyItem.getProductId());
                 throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

             }else if (product.getStock() < buyItem.getQuantity()){
                 log.warn("商品 {} 庫存數量不足，剩餘庫存 {} ， 欲購買數量 {}",
                         buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
             }
             //  計算商品庫存
             productDao.updateStock(product.getProductId(),product.getStock() - buyItem.getQuantity());


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

//        order 裡面包含多個 setOrderItemList
        order.setOrderItemList(orderItemList);

       return order;
    }
}
