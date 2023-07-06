package com.example.WarehouseDatabaseJava.model.product;

import com.example.WarehouseDatabaseJava.model.product.category.ProductCategoryRepository;
import com.example.WarehouseDatabaseJava.model.product.image.ProductImageRepository;
import com.example.WarehouseDatabaseJava.model.product.image.ProductImageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductImageService productImageService;

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

    //метод повернення списку продуктів для певної категорії (для покупця)
    public List<ProductDTO> getAllProductsByCategoryId(String categoryId) throws SQLException, IOException {
        if (!productRepository.existsByProductCategory_Id(categoryId)) {
            throw new EntityNotFoundException("Product category with id: " + categoryId + " not found");
        }

        List<Product> productList = productRepository.findAllByProductCategory_Id(categoryId);
        return setProductDTOList(productList);
    }

    //метод повернення списку продуктів для певної категорії, відсортовані по новизні продукту (для покупця)
    public List<ProductDTO> getAllProductsByCategoryIdSortByCreateTime(String categoryId) throws SQLException, IOException {
        if (!productRepository.existsByProductCategory_Id(categoryId)) {
            throw new EntityNotFoundException("Product category with id: " + categoryId + " not found");
        }

        //сортировка по времени создания записи продукта в базу
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<Product> productList = productRepository.findAllByProductCategory_Id(categoryId, sort);

        return setProductDTOList(productList);
    }

    //метод повернення списку всіх продуктів (DTO!) (для менеджера)
    public List<ProductDTO> getAllProductsDTO() throws SQLException, IOException {
        List<Product> productList = productRepository.findAll();
        return setProductDTOList(productList);
    }

    //метод отримання продукту по баркоду (DTO!)
    public ProductDTO searchProductByBarcode(String searchBarcode) throws SQLException, IOException {
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
    public List<ProductDTO> searchProductsByName(String searchName) throws SQLException, IOException {
        if (searchName == null) {
            throw new NullPointerException("searchName is null");
        }

        List<Product> productList = productRepository.findAllByNameContainingIgnoreCase(searchName);
        return setProductDTOList(productList);
    }

    //метод отримання списку продуктів по співпадінню найменування продукту та введеної строки ігноруючи реєстр
    //враховуючи категорію продукту
    public List<ProductDTO> searchProductsByNameWithCategory(String searchName, String categoryId) throws SQLException, IOException {
        if (searchName == null) {
            throw new NullPointerException("searchName is null");
        }
        if (!productCategoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category with id: " + categoryId + " not found");
        }

        List<Product> productList = productRepository.findAllByNameContainingIgnoreCaseAndProductCategory_Id(searchName, categoryId);
        return setProductDTOList(productList);
    }

    //внутрішній метод для встановлення інфи про певний продукт
    private ProductDTO setProductDTO(Product product) throws SQLException, IOException {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setBarcode(product.getBarcode());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setQuantity(product.getQuantity());
        if (product.getProductCategory() != null) {
            productDTO.setCategory(product.getProductCategory().getCategoryName());
        }
        //set image how bytes array
        if (product.getProductImage() != null) {
            Blob image = product.getProductImage().getImage();
            byte[] imageBytes = productImageService.convertBlobToByteArray(image);
            productDTO.setImage(imageBytes);
        }

        return productDTO;
    }

    //внутрішній метод для встановлення інфи про певний список продуктів
    private List<ProductDTO> setProductDTOList(List<Product> productList) throws SQLException, IOException {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            ProductDTO productDTO = setProductDTO(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
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

