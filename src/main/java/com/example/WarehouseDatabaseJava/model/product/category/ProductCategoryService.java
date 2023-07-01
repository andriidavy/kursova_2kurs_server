package com.example.WarehouseDatabaseJava.model.product.category;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    ProductRepository productRepository;

    // метод створення нової категорії продуктів (TESTED!)
    public ProductCategory createProductCategory(String categoryName) {
        if (productCategoryRepository.existsByCategoryName(categoryName)) {
            throw new IllegalArgumentException("ProductCategory with the name " + categoryName + " already exists");
        }

        ProductCategory productCategory = new ProductCategory(categoryName);
        return productCategoryRepository.save(productCategory);
    }

    //видалити категорію по її id (TESTED!)
    public void removeProductCategoryById(String categoryId) {
        if (!productCategoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category with id " + categoryId + " not found");
        }

        List<Product> productList = productRepository.findAllByProductCategory_Id(categoryId);

        for (Product product : productList) {
            product.setProductCategory(null);

            productRepository.save(product);
        }

        productCategoryRepository.deleteById(categoryId);
    }

    // отримання всіх категорій
    public List<ProductCategory> getAllProductCategory() {
        return productCategoryRepository.findAll();
    }

    // метод призначення певному продукту певної категорії (TESTED!)
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
