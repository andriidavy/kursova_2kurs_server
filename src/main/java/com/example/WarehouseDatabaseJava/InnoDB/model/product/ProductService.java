package com.example.WarehouseDatabaseJava.InnoDB.model.product;

import com.example.WarehouseDatabaseJava.MyISAM.model.product.ProductMyIsamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductMyIsamService.class);
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public int provideProduct(String productName, int quantity, double price, String description) {
        try {
            int productId;

            if (productRepository.isProductExistByName(productName)) {
                productId = productRepository.addProductQuantity(productName, quantity);
            } else {
                productId = productRepository.insertProduct(productName, quantity, price, description);
            }

            return productId;
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void saveDescriptionToProduct(int productId, String description) {
        productRepository.saveDescriptionForProduct(productId, description);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProductsList();
    }
}
