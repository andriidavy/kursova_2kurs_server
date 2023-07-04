package com.example.WarehouseDatabaseJava.model.product.image;

import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

@Service
public class ProductImageService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    //метод для додавання та оновлення зображення для продукту (TESTED!)
    public void addImageToProduct(String productId, String imagePath) throws IOException, SQLException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with ID: " + productId);
        }

        // Получаем продукт по его идентификатору
        Product product = productRepository.getReferenceById(productId);

        ProductImage productImage;

        // Проверяем наличие изображения, если еще нет, то создаем новую запись
        if (productImageRepository.existsByProductId(productId)) {
            productImage = productImageRepository.getReferenceByProductId(productId);
        } else {
            productImage = new ProductImage();
            // Связываем продукт с изображением
            productImage.setProduct(product);
        }

        // Устанавливаем новое изображение
        productImage.setImage(readImageFile(imagePath));

        // Сохраняем изображение
        productImageRepository.save(productImage);
    }

    // метод для видалення зображення для продукту (TESTED!)
    public void deleteImageForProduct(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with ID: " + productId);
        }
        if (!productImageRepository.existsByProductId(productId)) {
            throw new EntityNotFoundException("Image for product with ID: " + productId + " not found");
        }
        //Получаем значения продукта
        Product product = productRepository.getReferenceById(productId);

        // Получаем текущее изображение продукта
        ProductImage productImage = productImageRepository.getReferenceByProductId(productId);

        // Удаляем ссылку на изображение из объекта продукта
        product.setProductImage(null);

        // Удаляем изображение продукта из базы данных
        productImageRepository.delete(productImage);
    }

    //внутрішній метод перетворення зображення в Blob через посилання
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

    //внутрішній метод перетворення Blob в масив байтів (часткове, поетапне зчитування байтів)
    public byte[] convertBlobToByteArray(Blob blob) throws SQLException, IOException {
        try (InputStream inputStream = blob.getBinaryStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();

//            // Encode the byte array to Base64 (encoded bytes to string not using!)
//            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        }
    }

    //метод перетворення Blob в рядок Base64 (реалізація 2 - повне зчитування байтів і подальше перетворення в строку)
//    public static String convertBlobToBase64(Blob blob) throws SQLException, IOException {
//        // Get the length of the Blob
//        long blobLength = blob.length();
//
//        // Retrieve the bytes from the Blob using an InputStream
//        InputStream inputStream = blob.getBinaryStream();
//        byte[] blobBytes = new byte[(int) blobLength];
//        inputStream.read(blobBytes);
//
//        // Encode the byte array to Base64
//        String base64String = Base64.getEncoder().encodeToString(blobBytes);
//
//        // Close the InputStream
//        inputStream.close();
//
//        return base64String;
}
