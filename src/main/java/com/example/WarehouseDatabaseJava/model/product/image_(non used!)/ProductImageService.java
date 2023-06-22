//package com.example.WarehouseDatabaseJava.model.product.image_(non used!);
//
//import com.example.WarehouseDatabaseJava.model.product.Product;
//import com.example.WarehouseDatabaseJava.model.product.ProductRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.sql.SQLException;
//
//@Service
//public class ProductImageService {
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ProductImageRepository productImageRepository;
//
//    //метод для додавання та оновлення зображення для продукту
//    public void addImageToProduct(String productId, byte[] img) throws SQLException{
//        // Получаем продукт по его идентификатору
//        Product product = productRepository.getReferenceById(productId);
//        if (product == null) {
//            throw new IllegalArgumentException("Product not found with ID: " + productId);
//        }
//        // Проверяем наличие изображения, если еще нет, то создаем новую запись
//        ProductImage productImage = product.getProductImage();
//        if (productImage == null) {
//            productImage = new ProductImage();
//
//            // Связываем продукт с изображением
//            productImage.setProduct(product);
//        }
//
//        // Устанавливаем новое изображение
//        productImage.setImage(img);
//
//        // Сохраняем изображение
//        productImageRepository.save(productImage);
//    }
//
//    // метод для видалення зображення для продукту
//    public void deleteImageForProduct(String productId) {
//        // Получаем продукт по его идентификатору
//        Product product = productRepository.getReferenceById(productId);
//        if (product == null) {
//            throw new EntityNotFoundException("Product with id " + productId + " not found");
//        }
//        // Получаем текущее изображение продукта
//        ProductImage productImage = product.getProductImage();
//        if (productImage == null) {
//            throw new IllegalStateException("Product does not have an associated image_(non used!).");
//        }
//        // Удаляем изображение продукта из базы данных
//        productImageRepository.delete(productImage);
//    }
//
//}
