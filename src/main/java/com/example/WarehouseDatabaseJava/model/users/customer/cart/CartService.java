package com.example.WarehouseDatabaseJava.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    //додавання продукту в кошик покупцем
    public void addProductToCart(int customerId, int productId, int quantity) {
        Customer customer = customerRepository.getReferenceById(customerId);
        Product product = productRepository.getReferenceById(productId);
        if (customer.getCart() == null) {
            Cart cart = new Cart();
            customer.setCart(cart);
            cart.setCustomer(customer);
        }
        CartProduct cartProduct = findCartProductByProduct(customer.getCart(), product);
        if (cartProduct == null) {
            cartProduct = new CartProduct();
            cartProduct.setProduct(product);
            cartProduct.setQuantity(quantity);
            cartProduct.setCart(customer.getCart());
            customer.getCart().getCartProductList().add(cartProduct);
        }
    }

    //пошук співпадінь між продуктами які є в кошику і продуктом який хочемо додати в кошик
    private CartProduct findCartProductByProduct(Cart cart, Product product) {
        for (CartProduct cartProduct : cart.getCartProductList()) {
            if (cartProduct.getProduct().getId() == product.getId()) {
                return cartProduct;
            }
        }
        return null;
    }

    // видалення продукту з кошика покупцем
    public void removeProductFromCart(int customerId, int cartProductId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        CartProduct cartProduct = cartProductRepository.getReferenceById(cartProductId);
        if (customer.getCart() != null && cartProduct != null && cartProduct.getCart().getId() == customer.getCart().getId()) {
            customer.getCart().getCartProductList().remove(cartProduct);
            cartProductRepository.delete(cartProduct);
        }
    }

    // очищення кошика покупцем
    public void clearCart(int customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        if (customer.getCart() != null) {
            customer.getCart().getCartProductList().clear();
            cartRepository.save(customer.getCart());
        }
    }
}
