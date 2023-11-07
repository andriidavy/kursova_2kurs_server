package com.example.WarehouseDatabaseJava.MyISAM.model.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerDepartmentMyIsamService {
    @Autowired
    ManagerDepartmentMyIsamRepository managerDepartmentMyIsamRepository;

    public void assignManagerToDepartment(int managerId, int departmentId) {
        managerDepartmentMyIsamRepository.assignManagerToDepartment(managerId, departmentId);
    }

    public void removeDepartmentForManager(int managerId, int departmentId){
        managerDepartmentMyIsamRepository.removeDepartmentForManager(managerId, departmentId);
    }
}
