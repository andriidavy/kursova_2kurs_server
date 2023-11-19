package com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.dto.cart.CartProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    @Autowired
    CartRepository cartRepository;

    @Transactional
    public void addProductToCart(int customerId, int product_id, int quantity) {
        try {
            cartRepository.addProductToCart(customerId, product_id, quantity);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void deleteProductFromCart(int customerId, int productId) {
        try {
            cartRepository.deleteProductFromCart(customerId, productId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void clearCart(int customerId) {
        try {
            cartRepository.clearCart(customerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<CartProductDTO> getCartProductsByCustomerId(int customerId) {
        try {
            return cartRepository.getCartProductByCustomerId(customerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
