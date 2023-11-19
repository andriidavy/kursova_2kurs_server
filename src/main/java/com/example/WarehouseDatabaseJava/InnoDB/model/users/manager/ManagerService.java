package com.example.WarehouseDatabaseJava.InnoDB.model.users.manager;

import com.example.WarehouseDatabaseJava.InnoDB.model.department.DepartmentRepository;
import com.example.WarehouseDatabaseJava.dto.users.ManagerProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Transactional
    public Manager insertManager(String name, String surname, String email, String password) {
        try {
            managerRepository.insertManager(name, surname, email, password);
            return managerRepository.getLastInsertedManager(email);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void deleteManagerById(int managerId) {
        try {
            managerRepository.deleteManagerById(managerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Manager loginManager(String email, String password) {
        try {
            return managerRepository.loginManager(email, password);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ManagerProfileDTO getManagerProfile(int managerId) {
        try {
            Manager manager = managerRepository.getManagerById(managerId);

            int id = manager.getId();
            String name = manager.getName();
            String surname = manager.getSurname();
            String email = manager.getEmail();

            List<String> departmentsName = departmentRepository.findAllDepartmentsNameByManagerId(managerId);

            StringBuilder departmentsString = new StringBuilder();
            for (int i = 0; i < departmentsName.size(); i++) {
                String departmentName = departmentsName.get(i);
                departmentsString.append(departmentName);
                if (i < departmentsName.size() - 1) {
                    departmentsString.append(", ");
                }
            }
            return new ManagerProfileDTO(id, name, surname, email, departmentsString.toString());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ManagerProfileDTO> getAllManagers() {
        try {
            return managerRepository.getAllManagers().stream()
                    .map(manager -> new ManagerProfileDTO(manager.getId(), manager.getName(), manager.getSurname(), manager.getEmail()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}

