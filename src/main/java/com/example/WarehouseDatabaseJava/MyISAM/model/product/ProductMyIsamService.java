package com.example.WarehouseDatabaseJava.MyISAM.model.product;

import com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.CustomerMyIsamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerMyIsamService.class);
    @Autowired
    ProductMyIsamRepository productMyIsamRepository;

    @Transactional(value = "db2TransactionManager")
    public int provideProduct(String productName, int quantity) {
        try {
            int productId;

            if (productMyIsamRepository.isProductExistByName(productName)) {
                productId = productMyIsamRepository.addProductQuantity(productName, quantity);
            } else {
                productId = productMyIsamRepository.insertProduct(productName, quantity);
            }

            return productId;
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(value = "db2TransactionManager")
    public void saveDescriptionToProduct(int productId, String description) {
        productMyIsamRepository.saveDescriptionForProduct(productId, description);
    }

    public List<ProductMyISAM> getAllProducts() {
        return productMyIsamRepository.getAllProductsList();
    }
}
