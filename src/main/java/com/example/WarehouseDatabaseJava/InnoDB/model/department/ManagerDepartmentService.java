package com.example.WarehouseDatabaseJava.InnoDB.model.department;

import com.example.WarehouseDatabaseJava.InnoDB.model.users.manager.Manager;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.manager.ManagerRepository;
import com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerDepartmentService {
    @Autowired
    ManagerDepartmentRepository managerDepartmentRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    //метод призначення певному менеджеру певний етап
    @Transactional
    public void assignDepartmentToManager(int managerId, int departmentId) {

        if (!managerRepository.existsById(managerId)) {
            throw new RuntimeException("Manager not found with id: " + managerId);
        }

        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Department not found with id: " + managerId);
        }

        Manager manager = managerRepository.getReferenceById(managerId);
        Department department = departmentRepository.getReferenceById(departmentId);

        ManagerDepartment managerDepartment = new ManagerDepartment();
        managerDepartment.setManager(manager);
        managerDepartment.setDepartment(department);

        managerDepartmentRepository.save(managerDepartment);
    }

    //метод видалення для певного менеджера зв'язку з певним етапом
    @Transactional
    public void removeDepartmentFromManager(int managerId, int departmentId) {

        if (!managerRepository.existsById(managerId)) {
            throw new RuntimeException("Manager not found with id: " + managerId);
        }

        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Department not found with id: " + managerId);
        }

        if (!managerDepartmentRepository.existsByManagerIdAndDepartmentId(managerId, departmentId)) {
            throw new RuntimeException("Manager-Department relation not found" + "\nmanagerId: " + managerId + "\ndepartmentId: " + departmentId);
        }

        ManagerDepartment managerDepartment = managerDepartmentRepository.getReferenceByManagerIdAndDepartmentId(managerId, departmentId);

        managerDepartmentRepository.delete(managerDepartment);
    }

    //метод отримання всіх етапів, на які призначений певний менеджер
    @Transactional
    public List<DepartmentDTO> getAllDepartmentsForManager(int managerId) {

        if (!managerRepository.existsById(managerId)) {
            throw new RuntimeException("Manager not found with id: " + managerId);
        }

        List<Department> departmentList = departmentRepository.findAllByManagerId(managerId);

        return departmentList.stream()
                .map(department -> new DepartmentDTO(department.getId(), department.getDepartmentName()))
                .collect(Collectors.toList());
    }

    //метод отримання всіх етапів, на які певний менеджер НЕ призначений
    @Transactional
    public List<DepartmentDTO> getDepartmentsWithoutManager(int managerId) {

        if (!managerRepository.existsById(managerId)) {
            throw new RuntimeException("Manager not found with id: " + managerId);
        }

        List<Department> departmentList = departmentRepository.findAllByNotManagerId(managerId);

        return departmentList.stream()
                .map(department -> new DepartmentDTO(department.getId(), department.getDepartmentName()))
                .collect(Collectors.toList());
    }
}
