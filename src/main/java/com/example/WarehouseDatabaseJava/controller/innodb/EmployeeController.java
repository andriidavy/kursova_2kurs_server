package com.example.WarehouseDatabaseJava.controller.innodb;

import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.order.CustomService;
import com.example.WarehouseDatabaseJava.dto.report.ReportDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.order.report.ReportService;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.dto.users.EmployeeProfileDTO;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomService customService;
    @Autowired
    private ReportService reportService;

    //метод для логіну TESTED
    @GetMapping("/employee/login")
    public Employee loginEmployee(String email, String password){
        return employeeService.loginEmployee(email, password);
    }

    //отримати робітника по його id TESTED
    @GetMapping("/employee/get-employee-by-id")
    public EmployeeProfileDTO getEmployeeProfile(@RequestParam int employeeId) {
        return employeeService.getEmployeeProfile(employeeId);
    }

    // отримання списку всіх замовлень в процесі для конкретного робітника TESTED
    @GetMapping("/employee/custom/get-in-processing")
    public List<CustomDTO> getProcessingCustomsForEmployee(@RequestParam int employeeId){
        return customService.getProcessingCustomsForEmployee(employeeId);
    }

    // отримання списку всіх виконаних замовлень для конкретного робітника TESTED
    @GetMapping("/employee/custom/get-processed")
    public List<CustomDTO> getProcessedCustomsForEmployee(@RequestParam int employeeId){
        return customService.getProcessedCustomsForEmployee(employeeId);
    }

    //створення звіту для замовлення TESTED
    @PostMapping("/employee/custom/provide-report")
    public void provideReport(@RequestParam int employeeId, @RequestParam int customId, @RequestParam String reportText) {
        reportService.provideReport(employeeId, customId, reportText);
    }
    //отримати звіти зі статусом WAITING для конкретного робітника TESTED
    @GetMapping("/employee/get-waiting-reports-for-employee")
    public List<ReportDTO> getAllWaitingReportsForEmployee(@RequestParam int employeeId){
        return reportService.getAllWaitingReportsForEmployee(employeeId);
    }
    //отримати звіти зі статусом ACCEPTED для конкретного робітника TESTED
    @GetMapping("/employee/get-accepted-reports-for-employee")
    public List<ReportDTO> getAllAcceptedReportsForEmployee(@RequestParam int employeeId){
        return reportService.getAllAcceptedReportsForEmployee(employeeId);
    }
    //отримати звіти зі статусом REJECTED для конкретного робітника TESTED
    @GetMapping("/employee/get-rejected-reports-for-employee")
    public List<ReportDTO> getAllRejectedReportsForEmployee(@RequestParam int employeeId){
        return reportService.getAllRejectedReportsForEmployee(employeeId);
    }

    //встановити статус SENT для конкретного замовлення TESTED
    @PostMapping("/employee/custom/set-sent-status")
    public void setCustomSent(@RequestParam int customId){
        customService.setCustomSent(customId);
    }
}
