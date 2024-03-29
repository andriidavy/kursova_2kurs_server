package com.example.WarehouseDatabaseJava.model.users.customer;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

    @Entity
    public class Customer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String name;
        private String surname;
        private String email;
        private String password;

        //One-to-One relation with Cart
        @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
        @JsonIgnore
        private Cart cart;

        //One-to-Many relation with Custom
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
        @JsonIgnore
        private List<Custom> customList = new ArrayList<>();

        public Customer() {
        }

        public Customer(String name, String surname, String email, String password) {
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.password = password;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Cart getCart() {
            return cart;
        }

        public void setCart(Cart cart) {
            this.cart = cart;
        }

        public List<Custom> getCustomList() {
            return customList;
        }

        public void setCustomList(List<Custom> customList) {
            this.customList = customList;
        }
    }
