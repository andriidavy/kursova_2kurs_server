package com.example.WarehouseDatabaseJava.InnoDB.model.department;

import com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);
    @Autowired
    DepartmentRepository departmentRepository;

    @Transactional
    public void insertDepartment(String departName) {
        try {
            departmentRepository.insertDepartment(departName);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<DepartmentDTO> getAllDepartments() {
        try {
            return departmentRepository.getAllDepartments();
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<DepartmentDTO> getAllDepartmentsForManager(int managerId) {
        try {
            return departmentRepository.getAllDepartmentsForManager(managerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<DepartmentDTO> getAllDepartmentsWithoutManager(int managerId) {
        try {
            return departmentRepository.getAllDepartmentsWithoutManager(managerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
