package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.dto.cart.CartProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(CartMyIsamService.class);
    @Autowired
    CartMyIsamRepository cartMyIsamRepository;

    @Transactional(value = "db2TransactionManager")
    public void addProductToCart(int customerId, int product_id, int quantity) {
        try {
            cartMyIsamRepository.addProductToCart(customerId, product_id, quantity);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(value = "db2TransactionManager")
    public void deleteProductFromCart(int customerId, int productId) {
        try {
            cartMyIsamRepository.deleteProductFromCart(customerId, productId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(value = "db2TransactionManager")
    public void clearCart(int customerId) {
        try {
            cartMyIsamRepository.clearCart(customerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<CartProductDTO> getCartProductsByCustomerId(int customerId) {
        try {
            return cartMyIsamRepository.getCartProductByCustomerId(customerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
