package com.example.WarehouseDatabaseJava.model.users.manager.stage;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.order.CustomRepository;
import com.example.WarehouseDatabaseJava.model.users.manager.Manager;
import com.example.WarehouseDatabaseJava.model.users.manager.ManagerProfileDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CustomRepository customRepository;

    // збереження нового відділу (TESTED!)
    public Department save(String departmentName) {
        if (departmentRepository.existsByDepartmentName(departmentName)) {
            throw new RuntimeException("Department with name: " + departmentName + " already exists");
        }
        Department department = new Department(departmentName);
        return departmentRepository.save(department);
    }

    //видалити відділ по його id
    public void removeDepartmentById(String departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }

        departmentRepository.deleteById(departmentId);
    }

    // отримання всіх відділів (DTO!)
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();

        for (Department department : departments) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(department.getId());
            departmentDTO.setDepartmentName(department.getDepartmentName());
            departmentDTOs.add(departmentDTO);
        }

        return departmentDTOs;
    }

    //метод отримання всіх менеджерів, призначених на певний етап NOT USING IN CLIENT NOW!
    public List<ManagerProfileDTO> getAllManagersForDepartment(String departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }

        Department department = departmentRepository.getReferenceById(departmentId);
        List<ManagerProfileDTO> managerProfiles = new ArrayList<>();

        List<Manager> managers = department.getManagerList();

        for (Manager manager : managers) {
            ManagerProfileDTO managerProfileDTO = new ManagerProfileDTO(
                    manager.getName(),
                    manager.getSurname(),
                    manager.getEmail()
            );
            managerProfiles.add(managerProfileDTO);
        }
        return managerProfiles;
    }

//    //метод призначення конкретному замовленню конкретного відділу доставки (TESTED!)
//    //ОНОВЛЕНО!!!
//    public void assignDepartmentToCustom(String customId, String departmentId) {
//        if (!customRepository.existsById(customId)) {
//            throw new IllegalArgumentException("Custom not found with id: " + customId);
//        }
//        if (!departmentRepository.existsById(departmentId)) {
//            throw new EntityNotFoundException("Department not found with id: " + departmentId);
//        }
//
//        Custom custom = customRepository.getReferenceById(customId);
//        Department department = departmentRepository.getReferenceById(departmentId);
//
//        custom.setDepartment(department);
//        customRepository.save(custom);
//    }
}
