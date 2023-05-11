package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerCustom;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerCustomRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomService {
    @Autowired
    private CustomRepository customRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerCustomRepository customerCustomRepository;
    @Autowired
    private CustomProductRepository customProductRepository;

    //Створення замовлення покупцем
    public void createCustom(int customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        if (customer.getCart() != null && !customer.getCart().getCartProductList().isEmpty()) {
            Custom custom = new Custom();
            customRepository.save(custom);

            CustomerCustom customerCustom = new CustomerCustom();
            customerCustom.setCustomer(customer);
            customerCustom.setCustom(custom);
            customerCustomRepository.save(customerCustom);

            for (CartProduct cartProduct : customer.getCart().getCartProductList()) {
                CustomProduct customProduct = new CustomProduct();
                customProduct.setProduct(cartProduct.getProduct());
                customProduct.setQuantity(cartProduct.getQuantity());
                customProduct.setCustom(custom);
                customProductRepository.save(customProduct);
            }

            customer.getCart().getCartProductList().clear();
        }
    }
    // Отримання списку замовлень для конкретного покупця(по його id)
    public List<Custom> getCustomsForCustomer(int customerId){
        Customer customer = customerRepository.getReferenceById(customerId);
        List<Custom> customs = new ArrayList<>();
        for (CustomerCustom customerCustom : customer.getCustomerCustomList()) {
            customs.add(customerCustom.getCustom());
        }
        return customs;
    }

    // Отримання списку всіх замовлень від всіх покупців (Для Manager'а)
    public List<Custom> getAllCustoms(){
        return customRepository.findAll();
    }

}
