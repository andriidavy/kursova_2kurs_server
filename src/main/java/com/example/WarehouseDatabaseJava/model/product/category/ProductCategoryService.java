package com.example.WarehouseDatabaseJava.model.product.category;

import com.example.WarehouseDatabaseJava.model.users.manager.stage.Department;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentDTO;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    // метод створення нової категорії продуктів
    public ProductCategory createProductCategory(String categoryName) {
        ProductCategory existingProductCategory = productCategoryRepository.findByCategoryName(categoryName);
        if (existingProductCategory != null) {
            throw new IllegalArgumentException("ProductCategory with the same name already exists");
        }
        ProductCategory productCategory = new ProductCategory(categoryName);
        return productCategoryRepository.save(productCategory);
    }

    //видалити категорію по її id
    public void removeProductCategoryById(String categoryId) {
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
}
