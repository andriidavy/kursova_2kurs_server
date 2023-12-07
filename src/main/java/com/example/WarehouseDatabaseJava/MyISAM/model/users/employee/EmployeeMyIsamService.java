package com.example.WarehouseDatabaseJava.MyISAM.model.users.employee;

import com.example.WarehouseDatabaseJava.dto.users.EmployeeProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeMyIsamService.class);
    @Autowired
    EmployeeMyIsamRepository employeeMyIsamRepository;

    public int insertEmployee(String name, String surname, String email, String password, String repPassword) {
        try {
           return employeeMyIsamRepository.insertEmployee(name, surname, email, password, repPassword);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(value = "db2TransactionManager")
    public void deleteEmployeeById(int employeeId) {
        try {
            employeeMyIsamRepository.deleteEmployeeById(employeeId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public int loginEmployee(String email, String password) {
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

    public List<EmployeeProfileDTO> getAllEmployees() {
        try {
            return employeeMyIsamRepository.getAllEmployees().stream()
                    .map(employee -> new EmployeeProfileDTO(employee.getId(), employee.getName(), employee.getSurname(), employee.getEmail()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
