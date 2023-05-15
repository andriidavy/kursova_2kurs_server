package com.example.WarehouseDatabaseJava.model.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    //метод save зберігає новий запис якщо продукту з таким name ще немає і якщо є то додає до кількості цього продукту TESTED
    public Product save(Product product) {
        Product existingProduct = productRepository.findByName(product.getName());
        if (existingProduct != null) {
            existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
            return productRepository.save(existingProduct);
        } else {
            return productRepository.save(product);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
