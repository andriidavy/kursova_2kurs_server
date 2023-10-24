package com.example.WarehouseDatabaseJava.InnoDB.model.users.manager.stage;

import com.example.WarehouseDatabaseJava.InnoDB.model.order.Custom;
import com.example.WarehouseDatabaseJava.InnoDB.model.order.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CustomRepository customRepository;

    // збереження нового відділу
    @Transactional
    public Department save(String departmentName) {
        Department existingDepartment = departmentRepository.findByDepartmentName(departmentName);
        if (existingDepartment != null) {
            throw new IllegalArgumentException("Department with the same name already exists");
        }
        Department department = new Department(departmentName);
        return departmentRepository.save(department);
    }

    //видалити відділ по його id
    @Transactional
    public void removeDepartmentById(int departmentId){
        departmentRepository.deleteById(departmentId);
    }

    // отримання всіх відділів (DTO!)
    @Transactional
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
//    public List<ManagerProfileDTO> getAllManagersForDepartment(int departmentId) {
//        Department department = departmentRepository.getReferenceById(departmentId);
//        List<ManagerProfileDTO> managerProfiles = new ArrayList<>();
//        if (department != null) {
//            List<Manager> managers = department.getManagerList();
//
//            for (Manager manager : managers) {
//                ManagerProfileDTO managerProfileDTO = new ManagerProfileDTO(
//                        manager.getName(),
//                        manager.getSurname(),
//                        manager.getEmail()
//                );
//                managerProfiles.add(managerProfileDTO);
//            }
//        }
//        return managerProfiles;
//    }

    //метод призначення конкретному замовленню конкретного відділу доставки
    //ОНОВЛЕНО!!!
    public void assignDepartmentToCustom(int customId, int departmentId) {
        Custom custom = customRepository.getReferenceById(customId);
        if (custom == null) {
            throw new RuntimeException("Custom not found with id: " + customId);
        }

        Department department = departmentRepository.getReferenceById(departmentId);
        if (department == null) {
            throw new RuntimeException("Department not found with id: " + departmentId);
        }

        custom.setDepartment(department);
        customRepository.save(custom);
    }
}
