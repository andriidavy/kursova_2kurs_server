package com.example.WarehouseDatabaseJava.model.users.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    public void deleteById(Integer id){
        customerRepository.deleteById(id);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Streamable.of(customerRepository.findAll()).forEach(customers::add);
        return customers;
    }

}
