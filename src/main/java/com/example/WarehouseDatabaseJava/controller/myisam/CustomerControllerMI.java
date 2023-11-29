package com.example.WarehouseDatabaseJava.controller.myisam;

import com.example.WarehouseDatabaseJava.InnoDB.model.product.Product;
import com.example.WarehouseDatabaseJava.MyISAM.model.department.DepartmentMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.order.CustomMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.product.ProductMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.product.ProductMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.CustomerMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.CustomerMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart.CartMyIsamService;
import com.example.WarehouseDatabaseJava.dto.cart.CartProductDTO;
import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
import com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO;
import com.example.WarehouseDatabaseJava.dto.users.CustomerProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerControllerMI {
    @Autowired
    private CustomerMyIsamService customerMyIsamService;
    @Autowired
    private CartMyIsamService cartMyIsamService;
    @Autowired
    private CustomMyIsamService customMyIsamService;
    @Autowired
    private ProductMyIsamService productMyIsamService;
    @Autowired
    private DepartmentMyIsamService departmentMyIsamService;

    //CUSTOMER SIDE

    //метод для логіну TESTED
    @GetMapping("/mi/customer/login")
    public CustomerMyISAM loginCustomer(@RequestParam String email, @RequestParam String password) {
        return customerMyIsamService.loginCustomer(email, password);
    }

    //отримати покупця по його id TESTED
    @GetMapping("/mi/customer/get-customer-by-id")
    public CustomerProfileDTO getCustomerProfile(@RequestParam int customerId) {
        return customerMyIsamService.getCustomerProfile(customerId);
    }

    //зберегти покупця TESTED
    @PostMapping("/mi/customer/insert")
    public int insertCustomer(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password, @RequestParam String repPassword) {
        return customerMyIsamService.insertCustomer(name, surname, email, password, repPassword);
    }

    //CART SIDE

    //додати продукт до корзини конкретним покупцем TESTED
    @PostMapping("/mi/customer/cart/add-product-to-cart")
    public void addProductToCart(@RequestParam int customerId, @RequestParam int productId, @RequestParam int quantity) {
        cartMyIsamService.addProductToCart(customerId, productId, quantity);
    }

    //видалити продукт з корзини для конкретного покупця TESTED
    @DeleteMapping("/mi/customer/cart/remove-product-by-id")
    public void deleteProductFromCart(@RequestParam int customerId, @RequestParam int productId) {
        cartMyIsamService.deleteProductFromCart(customerId, productId);
    }

    //очистити корзину для конкретного покупця TESTED
        @DeleteMapping("/mi/customer/cart/clear")
    public void clearCart(@RequestParam int customerId) {
        cartMyIsamService.clearCart(customerId);
    }

    //CUSTOM SIDE

    //створення замовлення конкретним покупцем TESTED
    @PostMapping("/mi/customer/create-custom")
    public int createCustom(@RequestParam int customerId, @RequestParam int departmentId) {
        return customMyIsamService.createCustom(customerId, departmentId);
    }

    //отримати список всіх замовлень для конкретного покупця TESTED
    @GetMapping("/mi/customer/get-customs")
    public List<CustomDTO> getCustomsForCustomer(@RequestParam int customerId) {
        return customMyIsamService.getCustomsForCustomer(customerId);
    }

    //отримати список товарів в корзині для конкретного покупця TESTED
    @GetMapping("/mi/customer/get-cart")
    public List<CartProductDTO> getCartProductsByCustomerId(@RequestParam int customerId) {
        return cartMyIsamService.getCartProductsByCustomerId(customerId);
    }

    //отримати список всіх продуктів TESTED
    @GetMapping("/mi/customer/product/get-all")
    public List<ProductMyISAM> getAllProducts() {
        return productMyIsamService.getAllProducts();
    }

    //Шукати продукт TESTED
    @GetMapping("/mi/customer/product/search")
    public List<ProductMyISAM> searchProduct(@RequestParam String searchStr, @RequestParam int chooseType) {
        return productMyIsamService.searchProduct(searchStr, chooseType);
    }

    //шукати продукти за назвою та ціновим діапазоном TESTED
    @GetMapping("/mi/customer/product/search-with-price-range")
    public List<ProductMyISAM> searchProductWithPriceRange(@RequestParam String searchStr, @RequestParam int chooseType, @RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return productMyIsamService.searchProductWithPriceRange(searchStr, chooseType, minPrice, maxPrice);
    }

    //отримати мінімальну ціну на продукт TESTED
    @GetMapping("/mi/customer/product/get-min-price")
    public double getMinProductPrice(){
        return productMyIsamService.getMinProductPrice();
    }

    //отримати максимальну ціну на продукт TESTED
    @GetMapping("/mi/customer/product/get-max-price")
    public double getMaxProductPrice(){
        return productMyIsamService.getMaxProductPrice();
    }

    //отримати список всіх відділів TESTED
    @GetMapping("/mi/customer/department/get-all")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentMyIsamService.getAllDepartments();
    }
}
