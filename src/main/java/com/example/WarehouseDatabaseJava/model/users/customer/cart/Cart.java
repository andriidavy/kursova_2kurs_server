package com.example.WarehouseDatabaseJava.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Cart {

    private int id;

    //One-to-One relation with Customer
    private int customerId;
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "fk_customer_id", referencedColumnName = "customer_id")
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false, insertable = true, updatable = true)
    public int getCartId() {
        return id;
    }

    public void setCartId(int id) {
        this.id = id;
    }

    @Column(name="fk_customer_id", nullable = false, insertable = false, updatable = false)
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart cart)) return false;
        return id == cart.id && getCustomerId() == cart.getCustomerId() && Objects.equals(getCustomer(), cart.getCustomer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getCustomerId(), getCustomer());
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", customer=" + customer +
                '}';
    }
}

