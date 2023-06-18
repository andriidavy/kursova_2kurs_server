package com.example.WarehouseDatabaseJava.model.product.category;

import com.example.WarehouseDatabaseJava.model.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String categoryName;

    //One-To-Many relation with Product
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productCategory")
    @JsonIgnore
    List<Product> productList = new ArrayList<>();

    public ProductCategory() {
    }

    public ProductCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
