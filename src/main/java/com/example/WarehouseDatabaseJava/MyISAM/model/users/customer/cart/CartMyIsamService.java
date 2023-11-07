package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.MyISAM.model.product.ProductMyIsamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CartMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(ProductMyIsamService.class);
    @Autowired
    CartMyIsamRepository cartMyIsamRepository;

    public void addProductToCart(int customerId, int product_id, int quantity) {
        try {
            cartMyIsamRepository.addProductToCart(customerId, product_id, quantity);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteProductFromCart(int customerId, int productId) {
        try {
            cartMyIsamRepository.deleteProductFromCart(customerId, productId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void clearCart(int customerId) {
        try {
            cartMyIsamRepository.clearCart(customerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
