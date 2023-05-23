package com.example.WarehouseDatabaseJava.model.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    //метод save зберігає новий запис якщо продукту з таким name ще немає і якщо є то додає до кількості цього продукту TESTED
//    public Product save(Product product) {
//        Product existingProduct = productRepository.findByName(product.getName());
//        if (existingProduct != null) {
//            existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
//            return productRepository.save(existingProduct);
//        } else {
//            return productRepository.save(product);
//        }
//    }

    //метод save (Product) РЕАЛІЗАЦІЯ БЕЗ ВИКОРИСТАННЯ JPAREPOSITORY!!! TESTED
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Connection connection;

    public ProductService() {
        // Инициализация подключения к базе данных
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse_database", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product save(Product product) {
        Product existingProduct = findProductByName(product.getName());
        if (existingProduct != null) {
            int newQuantity = existingProduct.getQuantity() + product.getQuantity();
            updateProductQuantity(existingProduct.getId(), newQuantity);
            existingProduct.setQuantity(newQuantity);
            return existingProduct;
        } else {
            insertProduct(product);
            return product;
        }
    }

    private Product findProductByName(String name) {
        String query = "SELECT * FROM product WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int quantity = resultSet.getInt("quantity");
                return new Product(id, name, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateProductQuantity(int productId, int newQuantity) {
        String query = "UPDATE product SET quantity = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newQuantity);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertProduct(Product product) {
        String query = "INSERT INTO product (name, description , quantity) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
