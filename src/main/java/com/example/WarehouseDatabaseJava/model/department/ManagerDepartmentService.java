package com.example.WarehouseDatabaseJava.model.department;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerDepartmentService {
    private static final Logger logger = LoggerFactory.getLogger(ManagerDepartmentService.class);
    @Autowired
    ManagerDepartmentRepository managerDepartmentRepository;

    @Transactional
    public void assignManagerToDepartment(int managerId, int departmentId) {
        try {
            managerDepartmentRepository.assignManagerToDepartment(managerId, departmentId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void removeDepartmentForManager(int managerId, int departmentId) {
        try {
            managerDepartmentRepository.removeDepartmentForManager(managerId, departmentId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
