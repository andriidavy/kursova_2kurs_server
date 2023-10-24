package com.example.WarehouseDatabaseJava.InnoDB.model.users.manager;

import com.example.WarehouseDatabaseJava.InnoDB.model.users.manager.stage.Department;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.manager.stage.DepartmentRepository;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.manager.stage.ManagerDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    ManagerDepartmentRepository managerDepartmentRepository;

    //збереження менеджера
    @Transactional
    public Manager save(String name, String surname, String email, String password) {

        if (managerRepository.existsByEmail(email)) {
            throw new RuntimeException("Manager with the same email already exists");
        }

        Manager manager = new Manager(name, surname, email, password);
        managerRepository.save(manager);

        return manager;
    }

    //отримати список менеджерів
    @Transactional
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Transactional
    public Manager loginManager(String email, String password) {

        if (!managerRepository.existsByEmailAndPassword(email, password)) {
            throw new RuntimeException("Invalid email or password");
        }

        return managerRepository.getReferenceByEmailAndPassword(email, password);
    }


    //отримати список всіх профілів менеджерів
    @Transactional
    public List<ManagerProfileDTO> getAllManagersProfileDTO() {
        List<Manager> managers = managerRepository.findAll();

        return managers.stream()
                .map(manager -> new ManagerProfileDTO(manager.getId(), manager.getName(), manager.getSurname(), manager.getEmail()))
                .collect(Collectors.toList());
    }

    //видалення певного менеджера по його id
    @Transactional
    public void deleteManagerById(int managerId) {
        managerRepository.deleteById(managerId);
    }

    //отримати всіх список менеджерів (DTO!)
    @Transactional
    public ManagerProfileDTO getManagerProfile(int managerId) {

        if (!managerRepository.existsById(managerId)) {
            throw new RuntimeException("Manager not found with id: " + managerId);
        }

        Manager manager = managerRepository.getReferenceById(managerId);

        String name = manager.getName();
        String surname = manager.getSurname();
        String email = manager.getEmail();

        List<Department> departments = departmentRepository.findAllByManagerId(managerId); // Получаем список назначенных отделов для менеджера

        StringBuilder departmentsString = new StringBuilder();
        for (int i = 0; i < departments.size(); i++) {
            Department department = departments.get(i);
            departmentsString.append(department.getDepartmentName());
            if (i < departments.size() - 1) {
                departmentsString.append(", ");
            }
        }

        return new ManagerProfileDTO(name, surname, email, departmentsString.toString());
    }
}

