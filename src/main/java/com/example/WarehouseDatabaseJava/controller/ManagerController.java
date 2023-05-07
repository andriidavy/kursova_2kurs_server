package com.example.WarehouseDatabaseJava.controller;

import com.example.WarehouseDatabaseJava.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeService;
import com.example.WarehouseDatabaseJava.model.users.manager.Manager;
import com.example.WarehouseDatabaseJava.model.users.manager.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/manager/save")
    public Manager save(@RequestBody Manager manager){
        return managerService.save(manager);
    }

    @GetMapping("/manager/get-all")
    public List<Manager> getAllManagers(){
        return managerService.getAllManagers();
    }
}
