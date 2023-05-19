package com.example.WarehouseDatabaseJava.model.users.customer;

import com.example.WarehouseDatabaseJava.model.users.customer.cart.Cart;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartRepository cartRepository;

    //додавання нового покупця та його корзини TESTED
    public Customer save(Customer customer) {
        customerRepository.save(customer);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cartRepository.save(cart);

        return customer;
    }

    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerProfileDTO getCustomerProfile(int customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        if (customer == null) {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
        String name = customer.getName();
        String surname = customer.getSurname();
        String email = customer.getEmail();

        return new CustomerProfileDTO(name, surname, email);
    }
}
