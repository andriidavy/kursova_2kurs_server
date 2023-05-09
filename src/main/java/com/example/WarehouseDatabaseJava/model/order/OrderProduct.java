package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import jakarta.persistence.*;

@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Many-to-One relation with Order
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    // Many-to-One relation with Product
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
