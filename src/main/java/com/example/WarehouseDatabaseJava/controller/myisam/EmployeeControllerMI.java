package com.example.WarehouseDatabaseJava.controller.myisam;

import com.example.WarehouseDatabaseJava.MyISAM.model.order.CustomMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.order.report.ReportMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyIsamService;
import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
import com.example.WarehouseDatabaseJava.dto.report.ReportDTO;
import com.example.WarehouseDatabaseJava.dto.users.EmployeeProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeControllerMI {
    @Autowired
    EmployeeMyIsamService employeeMyIsamService;
    @Autowired
    ReportMyIsamService reportMyIsamService;
    @Autowired
    CustomMyIsamService customMyIsamService;

    //EMPLOYEE SIDE

    //метод для логіну TESTED
    @GetMapping("/mi/employee/login")
    public EmployeeMyISAM loginEmployee(@RequestParam String email, @RequestParam String password) {
        return employeeMyIsamService.loginEmployee(email, password);
    }

    //отримати робітника по його id TESTED
    @GetMapping("/mi/employee/get-employee-by-id")
    public EmployeeProfileDTO getEmployeeProfile(@RequestParam int employeeId) {
        return employeeMyIsamService.getEmployeeProfile(employeeId);
    }

    //CUSTOM SIDE

    //отримати список замовлень в процесі для робітника TESTED
    @GetMapping("/mi/employee/get-processing-customs-for-employee")
    public List<CustomDTO> getProcessingCustomsForEmployee(@RequestParam int employeeId){
        return customMyIsamService.getProcessingCustomsForEmployee(employeeId);
    }

    //отримати список виконаних замовлень для робітника TESTED
    @GetMapping("/mi/employee/get-processed-customs-for-employee")
    public List<CustomDTO> getProcessedCustomsForEmployee(@RequestParam int employeeId){
        return customMyIsamService.getProcessedCustomsForEmployee(employeeId);
    }

    //відправити замовлення TESTED
    @PostMapping("/mi/employee/set-custom-sent")
    public void setCustomSent(@RequestParam int customId){
        customMyIsamService.setCustomSent(customId);
    }

    //REPORT SIDE

    //відправити звіт TESTED
    @PostMapping("/mi/employee/provide-report")
    public void provideReport(@RequestParam int employeeId, @RequestParam int customId, @RequestParam String reportText) {
        reportMyIsamService.provideReport(employeeId, customId, reportText);
    }

    //отримати список звітів, що очікують відповідь, для робітника TESTED
    @GetMapping("/mi/employee/get-waiting-reports-for-employee")
    public List<ReportDTO> getAllWaitingReportsForEmployee(@RequestParam int employeeId){
       return reportMyIsamService.getAllWaitingReportsForEmployee(employeeId);
    }

    //отримати список прийнятих звітів для робітника TESTED
    @GetMapping("/mi/employee/get-accepted-reports-for-employee")
    public List<ReportDTO> getAllAcceptedReportsForEmployee(@RequestParam int employeeId){
        return reportMyIsamService.getAllAcceptedReportsForEmployee(employeeId);
    }

    //отримати список відхилених звітів для робітника TESTED
    @GetMapping("/mi/employee/get-rejected-reports-for-employee")
    public List<ReportDTO> getAllRejectedReportsForEmployee(@RequestParam int employeeId){
        return reportMyIsamService.getAllRejectedReportsForEmployee(employeeId);
    }
}
