package com.example.WarehouseDatabaseJava.MyISAM.model.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMyIsamService {
    @Autowired
    DepartmentMyIsamRepository departmentMyIsamRepository;

    public void insertDepartment(String departName) {
        departmentMyIsamRepository.insertDepartment(departName);
    }
}
