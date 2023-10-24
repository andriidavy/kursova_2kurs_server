package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CustomerMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerMyIsamService.class);
    @Autowired
    CustomerMyIsamRepository customerMyIsamRepository;

    public CustomerMyISAM saveCustomer(String name, String surname, String email, String password) {
        try {
            CustomerMyISAM customer = new CustomerMyISAM(name, surname, email, password);
            customerMyIsamRepository.saveAndFlush(customer);
            return customer;
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
}
