//package com.example.WarehouseDatabaseJava.model.product.image_(non used!);
//
//import com.example.WarehouseDatabaseJava.model.product.Product;
//import jakarta.persistence.*;
//
//
//@Entity
//public class ProductImage {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    String id;
//    byte[] image_(non used!);
//
//    // One-to-One relation with ProductImage
//    @OneToOne
//    @JoinColumn(name = "product_id")
//    Product product;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public byte[] getImage() {
//        return image_(non used!);
//    }
//
//    public void setImage(byte[] image_(non used!)) {
//        this.image_(non used!) = image_(non used!);
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//}
