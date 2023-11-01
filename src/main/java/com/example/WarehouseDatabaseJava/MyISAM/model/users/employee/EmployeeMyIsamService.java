package com.example.WarehouseDatabaseJava.MyISAM.model.users.employee;

import com.example.WarehouseDatabaseJava.dto.users.EmployeeProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeMyIsamService.class);
    @Autowired
    EmployeeMyIsamRepository employeeMyIsamRepository;

    @Transactional(value = "db2TransactionManager")
    public EmployeeMyISAM insertEmployee(String name, String surname, String email, String password) {
        try {
            employeeMyIsamRepository.insertEmployee(name, surname, email, password);
            return employeeMyIsamRepository.getLastInsertedEmployee(email);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public EmployeeMyISAM loginEmployee(String email, String password) {
        try {
            return employeeMyIsamRepository.loginEmployee(email, password);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public EmployeeProfileDTO getEmployeeProfile(int employeeId) {
        try {
            EmployeeMyISAM employee = employeeMyIsamRepository.getEmployeeById(employeeId);

            int id = employee.getId();
            String name = employee.getName();
            String surname = employee.getSurname();
            String email = employee.getEmail();

            return new EmployeeProfileDTO(id, name, surname, email);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
