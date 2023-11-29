package com.example.WarehouseDatabaseJava.InnoDB.model.users.employee;

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
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    EmployeeRepository employeeRepository;

    public int insertEmployee(String name, String surname, String email, String password, String repPassword) {
        try {
            return employeeRepository.insertEmployee(name, surname, email, password, repPassword);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void deleteEmployeeById(int employeeId) {
        try {
            employeeRepository.deleteEmployeeById(employeeId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Employee loginEmployee(String email, String password) {
        try {
            return employeeRepository.loginEmployee(email, password);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public EmployeeProfileDTO getEmployeeProfile(int employeeId) {
        try {
            Employee employee = employeeRepository.getEmployeeById(employeeId);

            return new EmployeeProfileDTO(employee.getId(), employee.getName(), employee.getSurname(), employee.getEmail());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<EmployeeProfileDTO> getAllEmployees() {
        try {
            return employeeRepository.getAllEmployees().stream()
                    .map(employee -> new EmployeeProfileDTO(employee.getId(), employee.getName(), employee.getSurname(), employee.getEmail()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
