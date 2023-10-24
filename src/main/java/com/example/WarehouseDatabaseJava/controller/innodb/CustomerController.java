package com.example.WarehouseDatabaseJava.controller.innodb;

import com.example.WarehouseDatabaseJava.InnoDB.model.order.CustomDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.order.CustomService;
import com.example.WarehouseDatabaseJava.InnoDB.model.product.Product;
import com.example.WarehouseDatabaseJava.InnoDB.model.product.ProductService;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.CustomerProfileDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.CustomerService;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.cart.CartProductDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.cart.CartService;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.manager.stage.DepartmentDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.manager.stage.DepartmentService;
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
    private ProductService productService;
    @Autowired
    private DepartmentService departmentService;

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
    public CustomerProfileDTO getCustomerProfile(@RequestParam int customerId) {
        return customerService.getCustomerProfile(customerId);
    }

    //зберегти покупця TESTED
    @PostMapping("/customer/save")
    public Customer save(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password) {
        return customerService.save(name, surname, email, password);
    }

    // додати продукт до корзини конкретним покупцем TESTED
    @PostMapping("/customer/cart/add-product-to-cart")
    public void addProductToCart(@RequestParam int customerId, @RequestParam int productId, @RequestParam int quantity) {
        cartService.addProductToCart(customerId, productId, quantity);
    }

    //видалити продукт з корзини для конкретного покупця TESTED
    @DeleteMapping("/customer/cart/remove-product-by-id")
    public void removeProductFromCart(@RequestParam int customerId, @RequestParam int productId) {
        cartService.removeProductFromCart(customerId, productId);
    }

    //очистити корзину для конкретного покупця
    @PostMapping("/customer/cart/clear")
    public void clearCart(@RequestParam int customerId) {
        cartService.clearCart(customerId);
    }

    //створення замовлення конкретним покупцем TESTED
    @PostMapping("/customer/create-custom")
    public int createCustom(@RequestParam int customerId) {
        return customService.createCustom(customerId);
    }

    //отримати список всіх замовлень для конкретного покупця TESTED
    @GetMapping("/customer/get-customs")
    public List<CustomDTO> getCustomsForCustomer(@RequestParam int customerId) {
        return customService.getCustomsForCustomer(customerId);
    }

    //отримати список товарів в корзині для конкретного покупця TESTED
    @GetMapping("/customer/get-cart")
    public List<CartProductDTO> getCartProductsByCustomerId(@RequestParam int customerId) {
        return cartService.getCartProductsByCustomerId(customerId);
    }

    //отримати список всіх продуктів TESTED
    @GetMapping("/customer/product/get-all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //призначити конкретний відділ для конкретного замовлення TESTED
    @PostMapping("/customer/custom/assign-department")
    public void assignDepartmentToCustom(@RequestParam int customId, @RequestParam int departmentId) {
        departmentService.assignDepartmentToCustom(customId, departmentId);
    }

    //отримати список всіх відділів TESTED
    @GetMapping("/customer/department/get-all")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}
