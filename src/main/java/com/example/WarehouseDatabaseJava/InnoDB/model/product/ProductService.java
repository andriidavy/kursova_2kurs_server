package com.example.WarehouseDatabaseJava.InnoDB.model.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
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

    public List<Product> searchProduct(String searchStr, int chooseQueryType) {

        switch (chooseQueryType) {
            case 0 -> {
                //натуральний запит (шукає по словах з пріорітетністю на найрелевантнішу схожість)
                try {
                    return productRepository.searchProductNatural(searchStr);
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 1 -> {
                //булевий запит (знаходить всі входження)
                String modifyBolStr = '*' + searchStr + '*';
                try {
                    return productRepository.searchProductBool(modifyBolStr);
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 2 -> {
                //розширений запит
                try {
                    return productRepository.searchProductExp(searchStr);
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            default -> {
                System.out.println("Invalid chooseQueryType");
                return null;
            }
        }
    }

    public List<Product> searchProductWithPriceRange(String searchStr, int chooseQueryType, Double minPrice, Double maxPrice) {

        switch (chooseQueryType) {
            case 0 -> {
                //натуральний запит (шукає по словах з пріорітетністю на найрелевантнішу схожість)
                try {
                    return productRepository.searchProductNaturalWithPriceRange(searchStr, minPrice, maxPrice);
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 1 -> {
                //булевий запит (знаходить всі входження)
                String modifyBolStr = '*' + searchStr + '*';
                try {
                    return productRepository.searchProductBoolWithPriceRange(modifyBolStr, minPrice, maxPrice);
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 2 -> {
                //розширений запит
                try {
                    return productRepository.searchProductExpWithPriceRange(searchStr, minPrice, maxPrice);
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            default -> {
                System.out.println("Invalid chooseQueryType");
                return null;
            }
        }
    }

    public double getMinProductPrice() {
        try {
            return productRepository.findMinPriceValue();
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public double getMaxProductPrice() {
        try {
            return productRepository.findMaxPriceValue();
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
