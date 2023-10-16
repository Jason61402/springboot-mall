package com.kujason.springbootmall.constant;

import com.kujason.springbootmall.model.Product;

public class MyTest {

//    psvm 快捷鍵
    public static void main(String[] args) {
        ProductCategory category = ProductCategory.FOOD;
        String s = category.name();
        System.out.println(s);  // 將 FOOD 轉成字串

        String s2 ="CAR";
        ProductCategory category2 = ProductCategory.valueOf(s2);
    }
}
