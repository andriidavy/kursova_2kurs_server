package com.example.WarehouseDatabaseJava.controller;

import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/product/get-all")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/product/save")
    public Product save(@RequestBody Product product){
        return productService.save(product);
    }
}
