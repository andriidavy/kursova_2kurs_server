package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.dto.cart.CartProductDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(CartMyIsamService.class);
    @Autowired
    CartMyIsamRepository cartMyIsamRepository;
    @PersistenceContext(unitName = "db2EntityManager")
    private EntityManager entityManager;

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

    @Transactional(readOnly = true)
    public List<CartProductDTO> getCartProductsByCustomerId(int customerId) {
        try {
            String nativeQuery = "SELECT p.id, p.name, cp.quantity, cp.price FROM cart_product_myisam cp JOIN cart_myisam c ON cp.cart_id = c.id JOIN product_myisam AS p ON cp.product_id = p.id WHERE c.customer_id = :customer_id";

            List<Object[]> resultList = entityManager.createNativeQuery(nativeQuery).setParameter("customer_id", customerId).getResultList();

            return resultList.stream().map(result -> new CartProductDTO(
                            (int) result[0],
                            (String) result[1],
                            (int) result[2],
                            ((BigDecimal) result[3]).doubleValue()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
