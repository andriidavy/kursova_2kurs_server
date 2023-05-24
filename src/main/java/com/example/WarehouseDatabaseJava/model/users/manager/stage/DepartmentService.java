package com.example.WarehouseDatabaseJava.model.users.manager.stage;

import com.example.WarehouseDatabaseJava.model.users.manager.Manager;
import com.example.WarehouseDatabaseJava.model.users.manager.ManagerProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    // збереження нового відділу
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    //видалити відділ по його id
    public void removeDepartmentById(int departmentId){
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

    //метод отримання всіх менеджерів, призначених на певний етап
    public List<ManagerProfileDTO> getAllManagersForDepartment(int departmentId) {
        Department department = departmentRepository.getReferenceById(departmentId);
        List<ManagerProfileDTO> managerProfiles = new ArrayList<>();
        if (department != null) {
            List<Manager> managers = department.getManagerList();

            for (Manager manager : managers) {
                ManagerProfileDTO managerProfileDTO = new ManagerProfileDTO(
                        manager.getName(),
                        manager.getSurname(),
                        manager.getEmail()
                );
                managerProfiles.add(managerProfileDTO);
            }
        }
        return managerProfiles;
    }
}
