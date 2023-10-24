package com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.InnoDB.model.product.Product;
import com.example.WarehouseDatabaseJava.InnoDB.model.product.ProductRepository;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    //додавання продукту в кошик покупцем TESTED
    public void addProductToCart(int customerId, int productId, int quantity) {
        Customer customer = customerRepository.getReferenceById(customerId);
        Product product = productRepository.getReferenceById(productId);
        Cart cart = customer.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            customer.setCart(cart);
        }
        // Проверяем наличие достаточного количества продукта в базе данных
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }
        CartProduct cartProduct = findCartProductByProduct(customer.getCart(), product);
        if (cartProduct == null) {
            cartProduct = new CartProduct();
            cartProduct.setProduct(product);
            cartProduct.setQuantity(quantity);
            cartProduct.setCart(customer.getCart());
            cart.getCartProductList().add(cartProduct);
            cartProductRepository.save(cartProduct);
            cartRepository.save(cart);
        } else {
            if (product.getQuantity() < cartProduct.getQuantity() + quantity) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }
            cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
            cartProductRepository.save(cartProduct);
            cartRepository.save(cart);
        }
    }

    //пошук співпадінь між продуктами які є в кошику і продуктом який хочемо додати в кошик TESTED
    private CartProduct findCartProductByProduct(Cart cart, Product product) {
        for (CartProduct cartProduct : cart.getCartProductList()) {
            if (cartProduct.getProduct().getId() == product.getId()) {
                return cartProduct;
            }
        }
        return null;
    }

    // видалення продукту з кошика покупцем
    public void removeProductFromCart(int customerId, int productId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        List<CartProduct> cartProductList = customer.getCart().getCartProductList();
        cartProductList.stream()
                .filter(cartProduct -> cartProduct.getProduct().getId() == productId)
                .findFirst()
                .ifPresent(cartProduct -> {
                    customer.getCart().getCartProductList().remove(cartProduct);
                    cartProductRepository.delete(cartProduct);
                });
    }

    // очищення кошика покупцем
    @Transactional
    public void clearCart(int customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        if (customer != null) {
            Cart cart = customer.getCart();
            if (cart != null) {
                cart.getCartProductList().clear();
                cartRepository.deleteAllCartProductsByCart(cart);
            }
        }
    }

    // отримати список товарів у корзині для певного покупця
    // (повертає список об'єктів класу що містить назву, кількість і id продукту що є в корзині) TESTED
    public List<CartProductDTO> getCartProductsByCustomerId(int customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);

        Cart cart = customer.getCart();
        List<CartProductDTO> cartProductDTOS = new ArrayList<>();

        for (CartProduct cartProduct : cart.getCartProductList()) {
            Product product = cartProduct.getProduct();
            int quantity = cartProduct.getQuantity();

            CartProductDTO cartProductDTO = new CartProductDTO();
            cartProductDTO.setProductId(product.getId());
            cartProductDTO.setProductName(product.getName());
            cartProductDTO.setQuantity(quantity);

            cartProductDTOS.add(cartProductDTO);
        }

        return cartProductDTOS;
    }
}
