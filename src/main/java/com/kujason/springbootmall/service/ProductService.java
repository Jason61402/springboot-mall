package com.kujason.springbootmall.service;

import com.kujason.springbootmall.constant.ProductCategory;
import com.kujason.springbootmall.dto.ProductRequest;
import com.kujason.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductCategory category,String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
