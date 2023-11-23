package com.example.WarehouseDatabaseJava.controller.innodb;

import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.order.CustomService;
import com.example.WarehouseDatabaseJava.InnoDB.model.product.Product;
import com.example.WarehouseDatabaseJava.InnoDB.model.product.ProductService;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.dto.users.CustomerProfileDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.CustomerService;
import com.example.WarehouseDatabaseJava.dto.cart.CartProductDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.cart.CartService;
import com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.department.DepartmentService;
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

    //метод для логіну TESTED
    @GetMapping("/customer/login")
    public Customer loginCustomer(@RequestParam String email, @RequestParam String password) {
        return customerService.loginCustomer(email, password);
    }

    //отримати покупця по його id TESTED
    @GetMapping("/customer/get-customer-by-id")
    public CustomerProfileDTO getCustomerProfile(@RequestParam int customerId) {
        return customerService.getCustomerProfile(customerId);
    }

    //зберегти покупця TESTED
    @PostMapping("/customer/insert")
    public Customer insertCustomer(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password) {
        return customerService.insertCustomer(name, surname, email, password);
    }

    // додати продукт до корзини конкретним покупцем TESTED
    @PostMapping("/customer/cart/add-product-to-cart")
    public void addProductToCart(@RequestParam int customerId, @RequestParam int productId, @RequestParam int quantity) {
        cartService.addProductToCart(customerId, productId, quantity);
    }

    //видалити продукт з корзини для конкретного покупця TESTED
    @DeleteMapping("/customer/cart/remove-product-by-id")
    public void deleteProductFromCart(@RequestParam int customerId, @RequestParam int productId) {
        cartService.deleteProductFromCart(customerId, productId);
    }

    //очистити корзину для конкретного покупця TESTED
    @DeleteMapping("/customer/cart/clear")
    public void clearCart(@RequestParam int customerId) {
        cartService.clearCart(customerId);
    }

    //створення замовлення конкретним покупцем TESTED
    @PostMapping("/customer/create-custom")
    public int createCustom(@RequestParam int customerId, @RequestParam int departmentId) {
        return customService.createCustom(customerId, departmentId);
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

    //отримати список всіх відділів TESTED
    @GetMapping("/customer/department/get-all")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}
