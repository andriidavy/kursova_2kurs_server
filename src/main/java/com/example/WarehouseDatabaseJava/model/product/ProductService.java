package com.example.WarehouseDatabaseJava.model.product;

import com.example.WarehouseDatabaseJava.model.order.CustomProduct;
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
import java.util.*;

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

    //метод повернення списку продуктів для певної категорії, відсортовані по популярності продукту (для покупця)
    //*популярність - кількість входжень продукту в замовлення за останні два тижні
    public List<ProductDTO> getAllProductsByCategoryIdSortByPopularity(String categoryId) throws SQLException, IOException {
        if (!productRepository.existsByProductCategory_Id(categoryId)) {
            throw new EntityNotFoundException("Product category with id: " + categoryId + " not found");
        }

        // Calculate the date two weeks ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, -2);
        Date twoWeeksAgo = calendar.getTime();

        List<Product> productList = productRepository.findAllByProductCategory_Id(categoryId);
        productList.sort((p1, p2) -> {
            int occurrencesP1 = getProductOccurrencesWithinTwoWeeks(p1, twoWeeksAgo);
            int occurrencesP2 = getProductOccurrencesWithinTwoWeeks(p2, twoWeeksAgo);
            return Integer.compare(occurrencesP2, occurrencesP1); // Sort in descending order of popularity
        });

        return setProductDTOList(productList);
    }

    //внутрішній метод отримання кількості входжень продукту в замовлення за останні два тижні (тут факт входження)
    private int getProductOccurrencesWithinTwoWeeks(Product product, Date twoWeeksAgo) {
        int count = 0;
        List<CustomProduct> customProducts = product.getCustomProductList();
        for (CustomProduct customProduct : customProducts) {
            Date customCreationTime = customProduct.getCustom().getCreationTime();
            if (customCreationTime != null && customCreationTime.after(twoWeeksAgo)) {
                count ++;
            }
        }
        return count;
    }

    //внутрішній метод отримання числа всіх входжень продукту в замовлення за останні два тижні (тут сумма входжень)
    private int getProductOccurrencesNumberWithinTwoWeeks(Product product, Date twoWeeksAgo) {
        int count = 0;
        List<CustomProduct> customProducts = product.getCustomProductList();
        for (CustomProduct customProduct : customProducts) {
            Date customCreationTime = customProduct.getCustom().getCreationTime();
            if (customCreationTime != null && customCreationTime.after(twoWeeksAgo)) {
                count += customProduct.getQuantity();
            }
        }
        return count;
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

    //внутрішній метод для отримання інфи про певний продукт
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
        productDTO.setImage(setImg(product));

        return productDTO;
    }

    //внутрішній метод отримання зображення для відображення продукта
    @Cacheable(value = "productImages", key = "#product.id")
    private byte[] setImg(Product product) throws SQLException, IOException {
        if (product.getProductImage() == null) {
            throw new NullPointerException("Image for product not exist");
        }

        Blob image = product.getProductImage().getImage();
        return productImageService.convertBlobToByteArray(image);
    }

    //внутрішній метод для отримання інфи про певний список продуктів
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

