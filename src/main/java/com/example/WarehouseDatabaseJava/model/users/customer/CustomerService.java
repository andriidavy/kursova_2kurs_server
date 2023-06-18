package com.example.WarehouseDatabaseJava.model.users.customer;

import com.example.WarehouseDatabaseJava.model.users.customer.cart.Cart;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartRepository cartRepository;

    //додавання нового покупця та його корзини TESTED

    public Customer save(String name, String surname, String email, String password) {
        Customer existingCustomer = customerRepository.findByEmail(email);
        if (existingCustomer != null) {
            throw new IllegalArgumentException("Customer with the same email already exists");
        }

        Customer customer = new Customer(name, surname, email, password);
        customerRepository.save(customer);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cartRepository.save(cart);

        return customer;
    }

    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    public void deleteById(String id) {
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
        throw new RuntimeException("Invalid email or password");
    }

    public CustomerProfileDTO getCustomerProfile(String customerId) {
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
