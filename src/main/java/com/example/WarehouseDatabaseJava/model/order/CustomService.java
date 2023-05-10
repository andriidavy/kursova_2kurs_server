package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomService {
    @Autowired
    private CustomRepository customRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public void createCustom(int customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        if (customer.getCart() != null && !customer.getCart().getCartProductList().isEmpty()) {
            Custom custom = new Custom();
            custom.setCustomer(customer);
            for(CartProduct cartProduct: customer.getCart().getCartProductList()){
                CustomProduct customProduct = new CustomProduct();
                customProduct.setProduct(cartProduct.getProduct());
                customProduct.setQuantity(cartProduct.getQuantity());
                customProduct.setOrder(custom);
                custom.getCustomProductList().add(customProduct);
            }
            customRepository.save(custom);
            customer.getCart().getCartProductList().clear();
        }
    }

}
