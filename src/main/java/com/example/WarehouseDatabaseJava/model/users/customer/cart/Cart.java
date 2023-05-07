package com.example.WarehouseDatabaseJava.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Cart {

    private int cartId;

    //One-to-One relation with Customer
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cart")
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        customer.setCart(this);
        this.customer = customer;
    }
    //


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", insertable = true, updatable = true)
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart cart)) return false;
        return cartId == cart.cartId && Objects.equals(getCustomer(), cart.getCustomer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, getCustomer());
    }
}
