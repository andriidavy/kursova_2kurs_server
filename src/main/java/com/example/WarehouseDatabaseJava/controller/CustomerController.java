package com.example.WarehouseDatabaseJava.controller;

import com.example.WarehouseDatabaseJava.model.order.CustomDTO;
import com.example.WarehouseDatabaseJava.model.order.CustomService;
import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductDTO;
import com.example.WarehouseDatabaseJava.model.product.ProductService;
import com.example.WarehouseDatabaseJava.model.product.category.ProductCategory;
import com.example.WarehouseDatabaseJava.model.product.category.ProductCategoryService;
import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerProfileDTO;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerService;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProductDTO;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartService;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentDTO;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
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
    @Autowired
    DepartmentService departmentService;
    @Autowired
    ProductCategoryService productCategoryService;

    //отримати список всіх покупців TESTED
    @GetMapping("/customer/get-all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    //метод для логіну
    @PostMapping("/customer/login")
    public Customer loginCustomer(@RequestParam String email, @RequestParam String password) {
        return customerService.loginCustomer(email, password);
    }

    //отримати покупця по його id
    @GetMapping("/customer/get-customer-by-id")
    public CustomerProfileDTO getCustomerProfile(@RequestParam String customerId) {
        return customerService.getCustomerProfile(customerId);
    }

    //зберегти покупця TESTED
    @PostMapping("/customer/save")
    public Customer save(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password) {
        return customerService.save(name, surname, email, password);
    }

    // додати продукт до корзини конкретним покупцем TESTED!
    @PostMapping("/customer/cart/add-product-to-cart")
    public void addProductToCart(@RequestParam String customerId, @RequestParam String productId, @RequestParam int quantity) {
        cartService.addProductToCart(customerId, productId, quantity);
    }

    //видалити продукт з корзини для конкретного покупця TESTED!
    @DeleteMapping("/customer/cart/remove-product-by-id")
    public void removeProductFromCart(@RequestParam String customerId, @RequestParam String productId) {
        cartService.removeProductFromCart(customerId, productId);
    }

    //очистити корзину для конкретного покупця TESTED!
    @PostMapping("/customer/cart/clear")
    public void clearCart(@RequestParam String customerId) {
        cartService.clearCart(customerId);
    }

    //створення замовлення конкретним покупцем TESTED! (upd: department assign into this fun(non tested!))
    @PostMapping("/customer/create-custom")
    public String createCustom(@RequestParam String customerId, String departmentId) {
        return customService.createCustom(customerId, departmentId);
    }

    //отримати список всіх замовлень для конкретного покупця TESTED
    @GetMapping("/customer/get-customs")
    public List<CustomDTO> getCustomsForCustomer(@RequestParam String customerId) {
        return customService.getCustomsForCustomer(customerId);
    }

    //отримати список товарів в корзині для конкретного покупця TESTED
    @GetMapping("/customer/get-cart")
    public List<CartProductDTO> getCartProductsByCustomerId(@RequestParam String customerId) {
        return cartService.getCartProductsByCustomerId(customerId);
    }

    //отримати ціну кошика для конкретного покупця
    @GetMapping("/customer/get-cart-price")
    public double getCartPriceByCustomerId(@RequestParam String customerId) {
        return cartService.getCartPriceByCustomerId(customerId);
    }

    //отримати список всіх продуктів для певної категорії TESTED!
    @GetMapping("/customer/category/product/get-all")
    public List<ProductDTO> getAllProductsByCategoryId(@RequestParam String categoryId) throws SQLException, IOException {
        return productService.getAllProductsByCategoryId(categoryId);
    }

    //пошук продуктів по найменуванню
    @GetMapping("/customer/product/search-by-name")
    public List<ProductDTO> searchProductsByName(@RequestParam String searchName) throws SQLException, IOException {
        return productService.searchProductsByName(searchName);
    }

    //пошук продуктів по найменуванню враховуючи категорію
    @GetMapping("/customer/category/product/search-by-name")
    public List<ProductDTO> searchProductsByNameWithCategory(@RequestParam String searchName, @RequestParam String categoryId) throws SQLException, IOException {
        return productService.searchProductsByNameWithCategory(searchName, categoryId);
    }

//    //призначити конкретний відділ для конкретного замовлення TESTED!
//    @PostMapping("/customer/custom/assign-department")
//    public void assignDepartmentToCustom(@RequestParam String customId, @RequestParam String departmentId) {
//        departmentService.assignDepartmentToCustom(customId, departmentId);
//    }

    //отримати список всіх відділів TESTED
    @GetMapping("/customer/department/get-all")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    //метод отримання всіх категорій товарів
    @GetMapping("/customer/category-product/get-all")
    public List<ProductCategory> getAllCategoryNames() {
        return productCategoryService.getAllProductCategory();
    }
}
