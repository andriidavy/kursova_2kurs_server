package com.example.WarehouseDatabaseJava.controller;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.order.CustomService;
import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerService;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomService customService;

    //customer methods
    @GetMapping("/customer/get-all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/customer/save")
    public Customer saveCustomer(@RequestBody Customer customer){
        return customerService.save(customer);
    }

    //cart methods
    @PostMapping("/customer/{customerId}/cart/add-product-to-cart")
    public void addProductToCart(@PathVariable int customerId, @RequestParam int productId, @RequestParam int quantity){
        cartService.addProductToCart(customerId, productId, quantity);
    }

    @DeleteMapping("/customer/{customerId}/cart/product/{cartProductId}")
    public void removeProductFromCart(@PathVariable int customerId, @PathVariable int cartProductId) {
        cartService.removeProductFromCart(customerId, cartProductId);
    }

    @PostMapping("/customer/{customerId}/cart/clear")
    public void clearCart(@PathVariable int customerId) {
        cartService.clearCart(customerId);
    }

    //custom methods
    @PostMapping("/customer/{customerId}/create-custom")
    public void createCustom(@PathVariable int customerId) {
        customService.createCustom(customerId);
    }

    @GetMapping("/customer/{customerId}/get-customs")
    public List<Custom> getCustomsForCustomer(@PathVariable int customerId){
        return customService.getCustomsForCustomer(customerId);
    }



}
