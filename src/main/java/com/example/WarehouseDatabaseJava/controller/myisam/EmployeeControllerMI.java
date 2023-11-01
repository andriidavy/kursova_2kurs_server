package com.example.WarehouseDatabaseJava.controller.myisam;

import com.example.WarehouseDatabaseJava.dto.users.EmployeeProfileDTO;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyIsamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeControllerMI {
    @Autowired
    EmployeeMyIsamService employeeMyIsamService;

    //зберегти робітника TESTED
    @PostMapping("/mi/employee/insert")
    public EmployeeMyISAM insertEmployee(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password) {
        return employeeMyIsamService.insertEmployee(name, surname, email, password);
    }

    //метод для логіну
    @GetMapping("/mi/employee/login")
    public EmployeeMyISAM loginEmployee(@RequestParam String email, @RequestParam String password) {
        return employeeMyIsamService.loginEmployee(email, password);
    }

    //отримати робітника по його id
    @GetMapping("/mi/employee/get-employee-by-id")
    public EmployeeProfileDTO getEmployeeProfile(@RequestParam int employeeId) {
        return employeeMyIsamService.getEmployeeProfile(employeeId);
    }
}
