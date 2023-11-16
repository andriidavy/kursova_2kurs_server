package com.example.WarehouseDatabaseJava.MyISAM.model.department;

import com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentMyIsamService.class);
    @Autowired
    DepartmentMyIsamRepository departmentMyIsamRepository;

    @Transactional(value = "db2TransactionManager")
    public void insertDepartment(String departName) {
        try {
            departmentMyIsamRepository.insertDepartment(departName);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<DepartmentDTO> getAllDepartments() {
        try {
            return departmentMyIsamRepository.getAllDepartments();
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<DepartmentDTO> getAllDepartmentsForManager(int managerId) {
        try {
            return departmentMyIsamRepository.getAllDepartmentsForManager(managerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<DepartmentDTO> getAllDepartmentsWithoutManager(int managerId) {
        try {
            return departmentMyIsamRepository.getAllDepartmentsWithoutManager(managerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
