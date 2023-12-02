package com.example.WarehouseDatabaseJava.InnoDB.model.users.customer;

import com.example.WarehouseDatabaseJava.dto.users.CustomerProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerRepository customerRepository;

    public int insertCustomer(String name, String surname, String email, String password, String repPassword) {
        try {
            return customerRepository.insertCustomer(name, surname, email, password, repPassword);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public int loginCustomer(String email, String password) {
        try {
            return customerRepository.loginCustomer(email, password);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public CustomerProfileDTO getCustomerProfile(int customerId) {
        try {
            Customer customer = customerRepository.getCustomerById(customerId);

            int id = customer.getId();
            String name = customer.getName();
            String surname = customer.getSurname();
            String email = customer.getEmail();

            return new CustomerProfileDTO(id, name, surname, email);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
