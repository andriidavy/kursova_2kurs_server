package com.example.WarehouseDatabaseJava.model.product.category;

import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    ProductRepository productRepository;

    // метод створення нової категорії продуктів
    public ProductCategory createProductCategory(String categoryName) {
        if (productCategoryRepository.existsByCategoryName(categoryName)) {
            throw new IllegalArgumentException("ProductCategory with the name " + categoryName + " already exists");
        }

        ProductCategory productCategory = new ProductCategory(categoryName);
        return productCategoryRepository.save(productCategory);
    }

    //видалити категорію по її id
    public void removeProductCategoryById(String categoryId) {
        if (!productCategoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category with id " + categoryId + " not found");
        }

        productCategoryRepository.deleteById(categoryId);
    }

    // отримання всіх категорій (DTO!)
    public List<ProductCategoryDTO> getAllDepartments() {
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        List<ProductCategoryDTO> productCategoryDTOs = new ArrayList<>();

        for (ProductCategory productCategory : productCategoryList) {
            ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
            productCategoryDTO.setCategoryName(productCategory.getCategoryName());
            productCategoryDTOs.add(productCategoryDTO);
        }
        return productCategoryDTOs;
    }

    //метод призначення певному продукту певної категорії
    public void assignProductToCategory(String productId, String categoryId) {

        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product with id " + productId + " not found");
        }
        if (!productCategoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category with id " + categoryId + " not found");
        }

        // Find the product and category by their IDs
        Product product = productRepository.getReferenceById(productId);
        ProductCategory category = productCategoryRepository.getReferenceById(categoryId);

        // Assign the category to the product
        product.setProductCategory(category);

        // Update the product in the repository
        productRepository.save(product);
    }
}
