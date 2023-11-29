package com.example.WarehouseDatabaseJava.MyISAM.model.users.manager;

import com.example.WarehouseDatabaseJava.dto.users.ManagerProfileDTO;
import com.example.WarehouseDatabaseJava.MyISAM.model.department.DepartmentMyIsamRepository;
import com.example.WarehouseDatabaseJava.dto.users.staff.StaffDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    @PersistenceContext(unitName = "db2EntityManager")
    private EntityManager entityManager;

    public int insertManager(String name, String surname, String email, String password, String repPassword) {
        try {
            return managerMyIsamRepository.insertManager(name, surname, email, password, repPassword);
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

            return new ManagerProfileDTO(id, name, surname, email, departmentsToString(departmentsName));
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ManagerProfileDTO> getAllManagers() {
        try {
            return managerMyIsamRepository.getAllManagers().stream()
                    .map(manager -> new ManagerProfileDTO(
                            manager.getId(),
                            manager.getName(),
                            manager.getSurname(),
                            manager.getEmail(),
                            departmentsToString(departmentMyIsamRepository.findAllDepartmentsNameByManagerId(manager.getId()))))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(value = "db2TransactionManager", readOnly = true)
    public List<StaffDTO> getStaffDTO() {
        try {
            String nativeQuery = "SELECT 'Employee' AS Type, id, name, surname, email FROM employee_myisam " +
                    "UNION SELECT 'Manager', id, name, surname, email FROM manager_myisam";

            List<Object[]> resultList = entityManager.createNativeQuery(nativeQuery).getResultList();

            return resultList.stream().map(result -> new StaffDTO(
                            (int) result[1],
                            (String) result[0],
                            (String) result[2],
                            (String) result[3],
                            (String) result[4]))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    private String departmentsToString(List<String> departmentsName) {
        StringBuilder departmentsString = new StringBuilder();
        for (int i = 0; i < departmentsName.size(); i++) {
            String departmentName = departmentsName.get(i);
            departmentsString.append(departmentName);
            if (i < departmentsName.size() - 1) {
                departmentsString.append(", ");
            }
        }
        return departmentsString.toString();
    }
}
