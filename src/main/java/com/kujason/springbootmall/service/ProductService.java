package com.kujason.springbootmall.service;

import com.kujason.springbootmall.dto.ProductRequest;
import com.kujason.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
