package com.example.WarehouseDatabaseJava.model.users.customer;

import com.example.WarehouseDatabaseJava.model.users.customer.cart.Cart;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartRepository cartRepository;

    //додавання нового покупця та його корзини (TESTED!)
    public Customer save(String name, String surname, String email, String password) {
        if (customerRepository.existsByEmail(email)) {
            throw new RuntimeException("Customer with email: " + email + " already exists");
        }

        Customer customer = new Customer(name, surname, email, password);
        customerRepository.save(customer);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cartRepository.save(cart);

        return customer;
    }

    //not used now!!!
    public void deleteCustomerById(String id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer loginCustomer(String email, String password) {
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        throw new IllegalArgumentException("Invalid email or password");
    }

    public CustomerProfileDTO getCustomerProfile(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer not found with id: " + customerId);
        }

        Customer customer = customerRepository.getReferenceById(customerId);

        String name = customer.getName();
        String surname = customer.getSurname();
        String email = customer.getEmail();

        return new CustomerProfileDTO(name, surname, email);
    }
}
