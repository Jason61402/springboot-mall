package com.kujason.springbootmall.service;

import com.kujason.springbootmall.dto.CreateOrderRequest;
import com.kujason.springbootmall.dto.OrderQueryParams;
import com.kujason.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
