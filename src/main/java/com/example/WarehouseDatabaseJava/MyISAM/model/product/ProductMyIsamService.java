package com.example.WarehouseDatabaseJava.MyISAM.model.product;

import com.example.WarehouseDatabaseJava.InnoDB.model.product.Product;
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
public class ProductMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(ProductMyIsamService.class);
    @Autowired
    ProductMyIsamRepository productMyIsamRepository;

    @Transactional(value = "db2TransactionManager")
    public int provideProduct(String productName, int quantity, double price, String description) {
        try {
            int productId;

            if (productMyIsamRepository.isProductExistByName(productName)) {
                productId = productMyIsamRepository.addProductQuantity(productName, quantity);
            } else {
                productId = productMyIsamRepository.insertProduct(productName, quantity, price, description);
            }

            return productId;
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Boolean isProductExistByName(String productName) {
        try {
            return productMyIsamRepository.isProductExistByName(productName);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(value = "db2TransactionManager")
    public void saveDescriptionToProduct(int productId, String description) {
        try {
            productMyIsamRepository.saveDescriptionForProduct(productId, description);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ProductDTO> getAllProducts() {
        try {
            return convertProductListToDTO(productMyIsamRepository.getAllProductsList());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ProductDTO> searchProduct(String searchStr, int chooseQueryType) {

        switch (chooseQueryType) {
            case 0 -> {
                //натуральний запит (шукає по словах з пріорітетністю на найрелевантнішу схожість)
                try {
                    return convertProductListToDTO(productMyIsamRepository.searchProductNatural(searchStr));
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 1 -> {
                //булевий запит (знаходить всі входження)
                String modifyBolStr = '*' + searchStr + '*';
                try {
                    return convertProductListToDTO(productMyIsamRepository.searchProductBool(modifyBolStr));
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 2 -> {
                //розширений запит
                try {
                    return convertProductListToDTO(productMyIsamRepository.searchProductExp(searchStr));
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
                    return convertProductListToDTO(productMyIsamRepository.searchProductNaturalWithPriceRange(searchStr, minPrice, maxPrice));
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 1 -> {
                //булевий запит (знаходить всі входження)
                String modifyBolStr = '*' + searchStr + '*';
                try {
                    return convertProductListToDTO(productMyIsamRepository.searchProductBoolWithPriceRange(modifyBolStr, minPrice, maxPrice));
                } catch (DataAccessException e) {
                    logger.error("An exception occurred: {}", e.getMessage(), e);
                    throw e;
                }
            }
            case 2 -> {
                //розширений запит
                try {
                    return convertProductListToDTO(productMyIsamRepository.searchProductExpWithPriceRange(searchStr, minPrice, maxPrice));
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
            ProductMyISAM product = productMyIsamRepository.searchProductById(productId);
            return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getPrice());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public double getMinProductPrice() {
        try {
            return productMyIsamRepository.findMinPriceValue();
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public double getMaxProductPrice() {
        try {
            return productMyIsamRepository.findMaxPriceValue();
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    private List<ProductDTO> convertProductListToDTO(List<ProductMyISAM> productList) {
        return productList.stream().map(
                product -> new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getPrice())
        ).collect(Collectors.toList());
    }
}
