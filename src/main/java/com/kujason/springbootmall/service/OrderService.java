package com.kujason.springbootmall.service;

import com.kujason.springbootmall.dto.CreateOrderRequest;
import com.kujason.springbootmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
