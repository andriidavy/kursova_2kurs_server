package com.example.WarehouseDatabaseJava.MyISAM.model.department;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerDepartmentMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(ManagerDepartmentMyIsamService.class);
    @Autowired
    ManagerDepartmentMyIsamRepository managerDepartmentMyIsamRepository;

    @Transactional(value = "db2TransactionManager")
    public void assignManagerToDepartment(int managerId, int departmentId) {
        try {
            managerDepartmentMyIsamRepository.assignManagerToDepartment(managerId, departmentId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(value = "db2TransactionManager")
    public void removeDepartmentForManager(int managerId, int departmentId) {
        try {
            managerDepartmentMyIsamRepository.removeDepartmentForManager(managerId, departmentId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
