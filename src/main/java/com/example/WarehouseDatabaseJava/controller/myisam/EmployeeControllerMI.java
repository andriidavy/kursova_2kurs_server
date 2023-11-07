package com.example.WarehouseDatabaseJava.controller.myisam;

import com.example.WarehouseDatabaseJava.MyISAM.model.order.CustomMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.order.report.ReportMyIsamService;
import com.example.WarehouseDatabaseJava.dto.users.EmployeeProfileDTO;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyIsamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeControllerMI {
    @Autowired
    EmployeeMyIsamService employeeMyIsamService;
    @Autowired
    ReportMyIsamService reportMyIsamService;
    @Autowired
    CustomMyIsamService customMyIsamService;

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

    //відправити звіт
    @PostMapping("/mi/employee/provide-report")
    public void provideReport(@RequestParam int customId, @RequestParam String reportText){
        reportMyIsamService.provideReport(customId, reportText);
    }

    //відправити замовлення
    @PostMapping("/mi/employee/set-custom-sent")
    public void setCustomSent(@RequestParam int customId){
        customMyIsamService.setCustomSent(customId);
    }
}
