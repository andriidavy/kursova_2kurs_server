package com.example.WarehouseDatabaseJava.model.users.manager;

import com.example.WarehouseDatabaseJava.model.users.manager.stage.Department;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentDTO;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    //збереження менеджера
    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    //видалення певного менеджера по його id
    public void deleteManagerById(int managerId) {
        managerRepository.deleteById(managerId);
    }

    //отримати всіх список менеджерів (DTO!)
    public ManagerProfileDTO getManagerProfile(int managerId) {
        Manager manager = managerRepository.getReferenceById(managerId);
        if (manager == null) {
            throw new RuntimeException("Manager not found with id: " + managerId);
        }
        String name = manager.getName();
        String surname = manager.getSurname();
        String email = manager.getEmail();

        return new ManagerProfileDTO(name, surname, email);
    }

    //метод призначення певному менеджеру певний етап
    public void assignDepartmentToManager(int managerId, int departmentId) {
        Manager manager = managerRepository.getReferenceById(managerId);
        Department department = departmentRepository.getReferenceById(departmentId);

        if (manager != null && department != null) {
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
    }

    //метод видалення для певного менеджера зв'язку з певним етапом
    public void removeDepartmentFromManager(int managerId, int departmentId) {
        Manager manager = managerRepository.getReferenceById(managerId);
        Department department = departmentRepository.getReferenceById(departmentId);

        if (manager != null && department != null) {
            List<Department> managerDepartments = manager.getDepartmentList();
            List<Manager> departmentManagers = department.getManagerList();

            // Удаляем этап из списка этапов менеджера
            if (managerDepartments.contains(department)) {
                managerDepartments.remove(department);
            }

            // Удаляем менеджера из списка менеджеров этапа
            if (departmentManagers.contains(manager)) {
                departmentManagers.remove(manager);
            }

            // Сохраняем изменения в базе данных
            managerRepository.save(manager);
            departmentRepository.save(department);
        }
    }

    //метод отримання всіх етапів, на які призначений певний менеджер
    public List<DepartmentDTO> getAllDepartmentsForManager(int managerId) {
        Manager manager = managerRepository.getReferenceById(managerId);
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();

        if (manager != null) {
            List<Department> departmentList = manager.getDepartmentList();

            for (Department department : departmentList) {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setId(department.getId());
                departmentDTO.setDepartmentName(department.getDepartmentName());
                departmentDTOList.add(departmentDTO);
            }
        }

        return departmentDTOList;
    }
}

