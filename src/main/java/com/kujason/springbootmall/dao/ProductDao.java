package com.kujason.springbootmall.dao;

import com.kujason.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
