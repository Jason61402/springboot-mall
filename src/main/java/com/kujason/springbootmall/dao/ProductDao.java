package com.kujason.springbootmall.dao;

import com.kujason.springbootmall.dto.ProductRequest;
import com.kujason.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);
}
