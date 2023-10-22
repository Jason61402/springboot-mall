package com.kujason.springbootmall.controller;

import com.kujason.springbootmall.constant.ProductCategory;
import com.kujason.springbootmall.dao.ProductQueryParams;
import com.kujason.springbootmall.dto.ProductRequest;
import com.kujason.springbootmall.model.Product;
import com.kujason.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(

            //  查詢條件 Filtering
            // (required = false) 變成非必填參數，前端有傳值才會進行確認
            // (defaultValue = "created_date") 設定預設值
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            //  排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            //  分頁 Pagination
            //  @Max @Min 需要 @Validated 才能生效
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
            ){

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        List<Product> productList = productService.getProducts(productQueryParams);

        return  ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product =  productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        Product product = productService.getProductById(productId);

        // 檢查商品是否存在
        if (product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // 修改商品的數據
        productService.updateProduct(productId,productRequest);

        Product updateProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping("/products/{productId}")
    public  ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }






}
