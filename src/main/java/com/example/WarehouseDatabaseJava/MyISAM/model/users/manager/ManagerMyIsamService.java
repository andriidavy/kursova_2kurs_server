package com.example.WarehouseDatabaseJava.MyISAM.model.users.manager;

import com.example.WarehouseDatabaseJava.dto.users.ManagerProfileDTO;
import com.example.WarehouseDatabaseJava.MyISAM.model.department.DepartmentMyIsamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(ManagerMyIsamService.class);
    @Autowired
    ManagerMyIsamRepository managerMyIsamRepository;
    @Autowired
    DepartmentMyIsamRepository departmentMyIsamRepository;

    @Transactional(value = "db2TransactionManager")
    public ManagerMyISAM insertManager(String name, String surname, String email, String password) {
        try {
            managerMyIsamRepository.insertManager(name, surname, email, password);
            return managerMyIsamRepository.getLastInsertedManager(email);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(value = "db2TransactionManager")
    public void deleteManagerById(int managerId) {
        try {
            managerMyIsamRepository.deleteManagerById(managerId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ManagerMyISAM loginManager(String email, String password) {
        try {
            return managerMyIsamRepository.loginManager(email, password);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ManagerProfileDTO getManagerProfile(int managerId) {
        try {
            ManagerMyISAM manager = managerMyIsamRepository.getManagerById(managerId);

            int id = manager.getId();
            String name = manager.getName();
            String surname = manager.getSurname();
            String email = manager.getEmail();

            List<String> departmentsName = departmentMyIsamRepository.findAllDepartmentsNameByManagerId(managerId);

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
            return managerMyIsamRepository.getAllManagers().stream()
                    .map(manager -> new ManagerProfileDTO(manager.getId(), manager.getName(), manager.getSurname(), manager.getEmail()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
