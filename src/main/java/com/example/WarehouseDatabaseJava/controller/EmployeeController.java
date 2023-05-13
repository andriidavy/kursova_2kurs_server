package com.example.WarehouseDatabaseJava.controller;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.order.CustomService;
import com.example.WarehouseDatabaseJava.model.order.report.Report;
import com.example.WarehouseDatabaseJava.model.order.report.ReportService;
import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeService;
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


    // отримання списку всіх замовлень в процесі для конкретного робітника
    @GetMapping("/employee/{employeeId}/custom/get-in-processing")
    public List<Custom> getProcessingCustomsForEmployee(@PathVariable int employeeId){
        return customService.getProcessingCustomsForEmployee(employeeId);
    }

    // отримання списку всіх виконаних замовлень для конкретного робітника
    @GetMapping("/employee/{employeeId}/custom/get-processed")
    public List<Custom> getProcessedCustomsForEmployee(@PathVariable int employeeId){
        return customService.getProcessedCustomsForEmployee(employeeId);
    }

    //створення звіту для замовлення
    @PostMapping("/employee/{employeeId}/custom/{customId}/create-report")
    public void createReport(int employeeId, int customId, String reportText) {
        reportService.createReport(employeeId,customId,reportText);
    }
    //отримати звіти зі статусом WAITING або REJECTED для конкретного робітника
    @GetMapping("/employee/{employeeId}/custom/get-waiting-or-rejected")
    public List<Report> getAllWaitingOrRejectedReports(int employeeId){
        return reportService.getAllWaitingOrRejectedReportsForEmployee(employeeId);
    }

    //встановити статус SENT для конкретного замовлення
    @PostMapping("/employee/custom/{customId}/set-sent-status")
    public void setCustomSent(@PathVariable int customId){
        customService.setCustomSent(customId);
    }

}
