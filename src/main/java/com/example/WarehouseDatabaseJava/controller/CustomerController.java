package com.example.WarehouseDatabaseJava.controller;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.order.CustomService;
import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductService;
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
    @Autowired
    ProductService productService;

    //отримати список всіх покупців
    @GetMapping("/customer/get-all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    //зберегти покупця
    @PostMapping("/customer/save")
    public Customer saveCustomer(@RequestBody Customer customer){
        return customerService.save(customer);
    }

    // додати продукт до корзини конкретним покупцем
    @PostMapping("/customer/cart/add-product-to-cart")
    public void addProductToCart(@RequestParam int customerId, @RequestParam int productId, @RequestParam int quantity){
        cartService.addProductToCart(customerId, productId, quantity);
    }

    //видалити продукт з корзини для конкретного покупця
    @DeleteMapping("/customer/{customerId}/cart/product/{cartProductId}")
    public void removeProductFromCart(@PathVariable int customerId, @PathVariable int cartProductId) {
        cartService.removeProductFromCart(customerId, cartProductId);
    }

    //очистити корзину для конкретного покупця
    @PostMapping("/customer/{customerId}/cart/clear")
    public void clearCart(@PathVariable int customerId) {
        cartService.clearCart(customerId);
    }

    //створення замовлення конкретним покупцем
    @PostMapping("/customer/create-custom")
    public void createCustom(@RequestParam int customerId) {
        customService.createCustom(customerId);
    }

    //отримати список всіх замовлень для конкретного покупця
    @GetMapping("/customer/{customerId}/get-customs")
    public List<Custom> getCustomsForCustomer(@PathVariable int customerId){
        return customService.getCustomsForCustomer(customerId);
    }

    //отримати список всіх продуктів
    @GetMapping("/customer/product/get-all")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
}
