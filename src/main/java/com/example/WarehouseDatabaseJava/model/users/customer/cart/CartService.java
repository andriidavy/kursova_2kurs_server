package com.example.WarehouseDatabaseJava.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public void addProductToCart(String customerId, String productId, int quantity) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer not found with id: " + customerId);
        }
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

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

            //установка новой цены корзины
            cart.setPrice(cart.getPrice() + (product.getPrice() * quantity));

            cartProductRepository.save(cartProduct);
            cartRepository.save(cart);
        } else {
            if (product.getQuantity() < cartProduct.getQuantity() + quantity) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            //установка новой цены корзины
            cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
            cart.setPrice(cart.getPrice() + (product.getPrice() * quantity));

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
    public void removeProductFromCart(String customerId, String productId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer not found with id: " + customerId);
        }
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }

        Customer customer = customerRepository.getReferenceById(customerId);
        Cart cart = customer.getCart();
        if (cart != null) {
            List<CartProduct> cartProductList = cart.getCartProductList();
            cartProductList.stream()
                    .filter(cartProduct -> Objects.equals(cartProduct.getProduct().getId(), productId))
                    .findFirst()
                    .ifPresent(cartProduct -> {
                        cart.getCartProductList().remove(cartProduct);
                        cartProductRepository.delete(cartProduct);
                        //установка новой цены корзины
                        cart.setPrice(cart.getPrice() - (cartProduct.getProduct().getPrice() * cartProduct.getQuantity()));
                        cartRepository.save(cart);
                    });
        }
    }

    // очищення кошика покупцем
    @Transactional
    public void clearCart(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer not found with id: " + customerId);
        }

        Customer customer = customerRepository.getReferenceById(customerId);
        Cart cart = customer.getCart();
        if (cart != null) {
            cart.getCartProductList().clear();
            cartRepository.deleteAllCartProductsByCart(cart);
            //установка новой цены корзины
            cart.setPrice(0);
            cartRepository.save(cart);
        }
    }

    // отримати список товарів у корзині для певного покупця
    // (повертає список об'єктів класу що містить назву, кількість і id продукту що є в корзині) TESTED
    public List<CartProductDTO> getCartProductsByCustomerId(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer not found with id: " + customerId);
        }

        Customer customer = customerRepository.getReferenceById(customerId);

        Cart cart = customer.getCart();
        List<CartProductDTO> cartProductDTOS = new ArrayList<>();

        for (CartProduct cartProduct : cart.getCartProductList()) {
            Product product = cartProduct.getProduct();
            int quantity = cartProduct.getQuantity();

            CartProductDTO cartProductDTO = new CartProductDTO();
            cartProductDTO.setProductId(product.getId());
            cartProductDTO.setProductName(product.getName());
            cartProductDTO.setProductPrice(product.getPrice());
            cartProductDTO.setQuantity(quantity);

            cartProductDTOS.add(cartProductDTO);
        }
        return cartProductDTOS;
    }
}
