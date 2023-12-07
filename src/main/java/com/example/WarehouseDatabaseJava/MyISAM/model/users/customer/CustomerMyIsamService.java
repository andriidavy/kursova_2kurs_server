package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer;

import com.example.WarehouseDatabaseJava.dto.users.CustomerProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerMyIsamService.class);
    @Autowired
    CustomerMyIsamRepository customerMyIsamRepository;

    public int insertCustomer(String name, String surname, String email, String password, String repPassword) {
        try {
            return customerMyIsamRepository.insertCustomer(name, surname, email, password, repPassword);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public int loginCustomer(String email, String password) {
        try {
            return customerMyIsamRepository.loginCustomer(email, password);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public CustomerProfileDTO getCustomerProfile(int customerId) {
        try {
            CustomerMyISAM customer = customerMyIsamRepository.getCustomerById(customerId);

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
