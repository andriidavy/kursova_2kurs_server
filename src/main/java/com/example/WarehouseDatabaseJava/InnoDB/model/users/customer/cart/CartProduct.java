package com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.InnoDB.model.product.Product;
import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_innodb", name = "cart_product")
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private int price;

    //Many-to-One relation with Cart
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    //Many-to-One relation with Product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
