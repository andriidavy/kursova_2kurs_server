package com.example.WarehouseDatabaseJava.model.product.image;

import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

@Service
public class ProductImageService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    //метод для додавання та оновлення зображення для продукту
    public void addImageToProduct(String productId, String imagePath)throws IOException, SQLException {
        if(!productRepository.existsById(productId)){
            throw new EntityNotFoundException("Product not found with ID: " + productId);
        }

        // Получаем продукт по его идентификатору
        Product product = productRepository.getReferenceById(productId);

        // Проверяем наличие изображения, если еще нет, то создаем новую запись
        ProductImage productImage = product.getProductImage();
        if (productImage == null) {
            productImage = new ProductImage();

            // Связываем продукт с изображением
            product.setProductImage(productImage);
        }

        // Устанавливаем новое изображение
        productImage.setImage(readImageFile(imagePath));

        // Сохраняем изображение
        productImageRepository.save(productImage);
    }

    // метод для видалення зображення для продукту
    public void deleteImageForProduct(String productId) {
        if(!productRepository.existsById(productId)){
            throw new EntityNotFoundException("Product not found with ID: " + productId);
        }
        // Получаем продукт по его идентификатору
        Product product = productRepository.getReferenceById(productId);

        // Получаем текущее изображение продукта
        ProductImage productImage = product.getProductImage();
        if (productImage == null) {
            throw new EntityNotFoundException("Product does not have an associated image.");
        }
        product.setProductImage(null);
        productRepository.save(product);

        // Удаляем изображение продукта из базы данных
        productImageRepository.delete(productImage);
    }

    //внутрішній метод перетворення зображення в масив байтів через посилання
    private Blob readImageFile(String imagePath) throws IOException, SQLException {
        File file = new File(imagePath);
        byte[] imageBytes = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            int bytesRead = fis.read(imageBytes);
            if (bytesRead < file.length()) {
                throw new IOException("Failed to read the complete image file: " + imagePath);
            }
        }

        return new SerialBlob(imageBytes);
    }
}
