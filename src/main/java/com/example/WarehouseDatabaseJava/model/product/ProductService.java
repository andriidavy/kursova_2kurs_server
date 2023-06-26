package com.example.WarehouseDatabaseJava.model.product;

import com.example.WarehouseDatabaseJava.model.product.category.ProductCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    //метод перевірки наявності товару з даним barcode (TESTED!)
    public boolean checkBarcode(long barcode) {
        return productRepository.existsByBarcode(barcode);
    }

    //метод додавання кількості уже наявного товару (TESTED!)
    public Product addProductQuantity(long barcode, int quantity) {
        if (!productRepository.existsByBarcode(barcode)) {
            throw new EntityNotFoundException("Product with barcode " + barcode + " not found");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        Product existingProduct = productRepository.getReferenceByBarcode(barcode);

        existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
        return productRepository.save(existingProduct);
    }

    //метод збереження нового товару (TESTED!)
    public Product saveProduct(long barcode, String name, double price, String description, int quantity) {
        if (barcode < 0) {
            throw new IllegalArgumentException("Invalid barcode");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Invalid price");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }
        if (name == null) {
            throw new NullPointerException("name is null");
        }
        if (description == null) {
            description = "Non description";
        }
        if (productRepository.existsByBarcode(barcode)) {
            throw new RuntimeException("Product with barcode: " + barcode + " already exists");
        }

        Product product = new Product(barcode, name, price, description, quantity);
        return productRepository.save(product);
    }

    //метод для додавання та оновлення url зображення для продукту (TESTED!)
    public void addImageUrlToProduct(String productId, String url) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product with id: " + productId + " not found");
        }
        if (url == null) {
            throw new NullPointerException("url is null");
        }

        // Получаем продукт по его идентификатору
        Product product = productRepository.getReferenceById(productId);

        // Устанавливаем новое url изображения
        product.setImageUrl(url);

        productRepository.save(product);
    }

    // метод для видалення url зображення для продукту (TESTED!)
    public void deleteImageForProduct(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product with id: " + productId + " not found");
        }

        // Получаем продукт по его идентификатору
        Product product = productRepository.getReferenceById(productId);

        product.setImageUrl(null);
        productRepository.save(product);
    }

    //метод повернення списку продуктів для певної категорії (для покупця)
    public List<Product> getAllProductsByCategoryId(String categoryId) {
        if (!productRepository.existsByProductCategory_Id(categoryId)) {
            throw new EntityNotFoundException("Product category with id: " + categoryId + " not found");
        }

        return productRepository.findAllByProductCategory_Id(categoryId);
    }

    //метод повернення списку всіх продуктів (DTO!) (для менеджера)
    public List<ProductDTO> getAllProductsDTO() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            ProductDTO productDTO = setProductDTO(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    //метод отримання продукту по баркоду (DTO!)
    public ProductDTO searchProductByBarcode(String searchBarcode) {
        if (!searchBarcode.matches("\\d+")) {
            throw new IllegalArgumentException("Argument: " + searchBarcode + " is not number");
        }

        long barcode = Long.parseLong(searchBarcode);

        if (!productRepository.existsByBarcode(barcode)) {
            throw new EntityNotFoundException("Product with barcode: " + barcode + " not found");
        }

        Product product = productRepository.getReferenceByBarcode(barcode);

        return setProductDTO(product);
    }

    //метод отримання списку продуктів по співпадінню найменування продукту та введеної строки ігноруючи реєстр
    public List<Product> searchProductsByName(String searchName) {
        if (searchName == null) {
            throw new NullPointerException("searchName is null");
        }
        return productRepository.findAllByNameContainingIgnoreCase(searchName);
    }

    //метод отримання списку продуктів по співпадінню найменування продукту та введеної строки ігноруючи реєстр
    //враховуючи категорію продукту
    public List<Product> searchProductsByNameWithCategory(String searchName, String categoryId) {
        if (searchName == null) {
            throw new NullPointerException("searchName is null");
        }
        if (!productCategoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category with id: " + categoryId + " not found");
        }

        return productRepository.findAllByNameContainingIgnoreCaseAndProductCategory_Id(searchName, categoryId);
    }

    //внутрішній метод для встановлення інфи про певний продукт
    private ProductDTO setProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setBarcode(product.getBarcode());
        productDTO.setName(productDTO.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setCategory(product.getProductCategory().getCategoryName());
        return productDTO;
    }
}


//метод save (Product) РЕАЛІЗАЦІЯ БЕЗ ВИКОРИСТАННЯ JPAREPOSITORY!!! TESTED
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    private Connection connection;
//
//    public ProductService() {
//        // Инициализация подключения к базе данных
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse_database", "root", "root");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Product save(Product product) {
//        Product existingProduct = findProductByName(product.getName());
//        if (existingProduct != null) {
//            int newQuantity = existingProduct.getQuantity() + product.getQuantity();
//            updateProductQuantity(existingProduct.getId(), newQuantity);
//            existingProduct.setQuantity(newQuantity);
//            return existingProduct;
//        } else {
//            insertProduct(product);
//            return product;
//        }
//    }
//
//    private Product findProductByName(String name) {
//        String query = "SELECT * FROM product WHERE name = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, name);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                int quantity = resultSet.getInt("quantity");
//                return new Product(id, name, quantity);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private void updateProductQuantity(int productId, int newQuantity) {
//        String query = "UPDATE product SET quantity = ? WHERE id = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, newQuantity);
//            statement.setInt(2, productId);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void insertProduct(Product product) {
//        String query = "INSERT INTO product (name, description , quantity) VALUES (?, ?, ?)";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, product.getName());
//            statement.setString(2, product.getDescription());
//            statement.setInt(3, product.getQuantity());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//////////////////////////////////////////////////////////////////////////////////////

