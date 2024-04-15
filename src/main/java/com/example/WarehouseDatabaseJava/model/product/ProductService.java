package com.example.WarehouseDatabaseJava.model.product;

import com.example.WarehouseDatabaseJava.dto.product.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public Boolean isProductExistByName(String productName) {
        try {
            return productRepository.isProductExistByName(productName);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void saveDescriptionToProduct(int productId, String description) {
        productRepository.saveDescriptionForProduct(productId, description);
    }

    public List<ProductDTO> getAllProducts() {
        return convertProductListToDTO(productRepository.getAllProductsList());
    }

    public List<ProductDTO> searchProduct(String searchStr, int chooseQueryType) {

        switch (chooseQueryType) {
            case 0 -> {
                //натуральний запит (шукає по словах з пріорітетністю на найрелевантнішу схожість)
                try {
                    return convertProductListToDTO(productRepository.searchProductNatural(searchStr));
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 1 -> {
                //булевий запит (знаходить всі входження)
                String modifyBolStr = '*' + searchStr + '*';
                try {
                    return convertProductListToDTO(productRepository.searchProductBool(modifyBolStr));
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 2 -> {
                //розширений запит
                try {
                    return convertProductListToDTO(productRepository.searchProductExp(searchStr));
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

    public List<ProductDTO> searchProductWithPriceRange(String searchStr, int chooseQueryType, Double minPrice, Double maxPrice) {

        switch (chooseQueryType) {
            case 0 -> {
                //натуральний запит (шукає по словах з пріорітетністю на найрелевантнішу схожість)
                try {
                    return convertProductListToDTO(productRepository.searchProductNaturalWithPriceRange(searchStr, minPrice, maxPrice));
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 1 -> {
                //булевий запит (знаходить всі входження)
                String modifyBolStr = '*' + searchStr + '*';
                try {
                    return convertProductListToDTO(productRepository.searchProductBoolWithPriceRange(modifyBolStr, minPrice, maxPrice));
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 2 -> {
                //розширений запит
                try {
                    return convertProductListToDTO(productRepository.searchProductExpWithPriceRange(searchStr, minPrice, maxPrice));
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

    public ProductDTO searchProductById(int productId) {
        try {
            Product product = productRepository.searchProductById(productId);
            return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getPrice());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
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

    private List<ProductDTO> convertProductListToDTO(List<Product> productList) {
        return productList.stream().map(
                product -> new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getPrice())
        ).collect(Collectors.toList());
    }
}
