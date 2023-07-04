package com.example.WarehouseDatabaseJava.model.product;

import com.example.WarehouseDatabaseJava.model.order.CustomProduct;
import com.example.WarehouseDatabaseJava.model.product.category.ProductCategory;
import com.example.WarehouseDatabaseJava.model.product.image.ProductImage;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private long barcode;
    private String name;
    private double price;
    private String description;
    private int quantity;

    // One-to-One relation with ProductImage (SAVING IMAGE TO DATABASE)
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private ProductImage productImage;

    // One-to-Many relation with CartProduct
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartProduct> cartProductList = new ArrayList<>();

    // One-to-Many relation with CustomProduct
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CustomProduct> customProductList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

    public Product() {
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Product(long barcode, String name, double price, String description, int quantity) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CartProduct> getCartProductList() {
        return cartProductList;
    }

    public void setCartProductList(List<CartProduct> cartProductList) {
        this.cartProductList = cartProductList;
    }

    public List<CustomProduct> getCustomProductList() {
        return customProductList;
    }

    public void setCustomProductList(List<CustomProduct> customProductList) {
        this.customProductList = customProductList;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }


    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
}
