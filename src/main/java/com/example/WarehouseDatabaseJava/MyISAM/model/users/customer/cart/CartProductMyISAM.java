package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_myisam", name = "cart_product_myisam")
public class CartProductMyISAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private int price;
    @Column(name = "cart_id")
    private int cartId;
    @Column(name = "product_id")
    private int productId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
