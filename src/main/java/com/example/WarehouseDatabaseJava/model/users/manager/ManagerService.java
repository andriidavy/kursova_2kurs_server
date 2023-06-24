package com.example.WarehouseDatabaseJava.model.users.manager;

import com.example.WarehouseDatabaseJava.model.users.manager.stage.Department;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentDTO;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    //збереження менеджера
    public Manager save(String name, String surname, String email, String password) {
        if (managerRepository.existsByEmail(email)) {
            throw new RuntimeException("Manager with email: " + email + " already exists");
        }

        Manager manager = new Manager(name, surname, email, password);
        managerRepository.save(manager);

        return manager;
    }

    //отримати список менеджерів
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Manager loginManager(String email, String password) {
        List<Manager> managers = managerRepository.findAll();
        for (Manager manager : managers) {
            if (manager.getEmail().equals(email) && manager.getPassword().equals(password)) {
                return manager;
            }
        }
        throw new IllegalArgumentException("Invalid email or password");
    }


    //отримати список всіх профілів менеджерів
    public List<ManagerProfileDTO> getAllManagersProfileDTO() {
        List<Manager> managers = managerRepository.findAll();
        return managers.stream()
                .map(manager -> new ManagerProfileDTO(manager.getName(), manager.getSurname(), manager.getEmail()))
                .collect(Collectors.toList());
    }

    //видалення певного менеджера по його id
    public void deleteManagerById(String managerId) {
        if (!managerRepository.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id: " + managerId);
        }
        managerRepository.deleteById(managerId);
    }

    //отримати всіх список менеджерів (DTO!)

    public ManagerProfileDTO getManagerProfile(String managerId) {
        if (!managerRepository.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id: " + managerId);
        }

        Manager manager = managerRepository.getReferenceById(managerId);

        String name = manager.getName();
        String surname = manager.getSurname();
        String email = manager.getEmail();

        List<Department> departments = manager.getDepartmentList(); // Получаем список назначенных отделов для менеджера

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

    //метод призначення певному менеджеру певний етап
    public void assignDepartmentToManager(String managerId, String departmentId) {
        if (!managerRepository.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id: " + managerId);
        }
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }

        Manager manager = managerRepository.getReferenceById(managerId);
        Department department = departmentRepository.getReferenceById(departmentId);

        List<Department> managerDepartments = manager.getDepartmentList();
        List<Manager> departmentManagers = department.getManagerList();

        // Добавляем этап в список этапов менеджера, если его там нет
        if (!managerDepartments.contains(department)) {
            managerDepartments.add(department);
        }

        // Добавляем менеджера в список менеджеров этапа, если его там нет
        if (!departmentManagers.contains(manager)) {
            departmentManagers.add(manager);
        }

        // Сохраняем изменения в базе данных
        managerRepository.save(manager);
        departmentRepository.save(department);
    }

    //метод видалення для певного менеджера зв'язку з певним етапом
    public void removeDepartmentFromManager(String managerId, String departmentId) {
        if (!managerRepository.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id: " + managerId);
        }
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }

        Manager manager = managerRepository.getReferenceById(managerId);
        Department department = departmentRepository.getReferenceById(departmentId);

        List<Department> managerDepartments = manager.getDepartmentList();
        List<Manager> departmentManagers = department.getManagerList();

        // Удаляем этап из списка этапов менеджера
        managerDepartments.remove(department);

        // Удаляем менеджера из списка менеджеров этапа
        departmentManagers.remove(manager);

        // Сохраняем изменения в базе данных
        managerRepository.save(manager);
        departmentRepository.save(department);
    }

    //метод отримання всіх етапів, на які призначений певний менеджер
    public List<DepartmentDTO> getAllDepartmentsForManager(String managerId) {
        if (!managerRepository.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id: " + managerId);
        }

        Manager manager = managerRepository.getReferenceById(managerId);
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();

        List<Department> departmentList = manager.getDepartmentList();

        for (Department department : departmentList) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(department.getId());
            departmentDTO.setDepartmentName(department.getDepartmentName());
            departmentDTOList.add(departmentDTO);
        }
        return departmentDTOList;
    }

    //метод отримання всіх етапів, на які певний менеджер НЕ призначений
    public List<DepartmentDTO> getDepartmentsWithoutManager(String managerId) {
        if (!managerRepository.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id: " + managerId);
        }

        Manager manager = managerRepository.getReferenceById(managerId);
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();

        for (Department department : departments) {
            if (!department.getManagerList().contains(manager)) {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setId(department.getId());
                departmentDTO.setDepartmentName(department.getDepartmentName());
                departmentDTOs.add(departmentDTO);
            }
        }
        return departmentDTOs;
    }
}

