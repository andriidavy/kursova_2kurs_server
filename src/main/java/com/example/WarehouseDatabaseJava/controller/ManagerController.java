package com.example.WarehouseDatabaseJava.controller;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.order.CustomService;
import com.example.WarehouseDatabaseJava.model.order.report.Report;
import com.example.WarehouseDatabaseJava.model.order.report.ReportService;
import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductService;
import com.example.WarehouseDatabaseJava.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeService;
import com.example.WarehouseDatabaseJava.model.users.manager.Manager;
import com.example.WarehouseDatabaseJava.model.users.manager.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private CustomService customService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ProductService productService;
    @Autowired
    ReportService reportService;

    //зберегти нового менеджера
    @PostMapping("/manager/save")
    public Manager save(@RequestBody Manager manager) {
        return managerService.save(manager);
    }

    //отримати список всіх менеджерів
    @GetMapping("/manager/get-all")
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

//    //отримати список всіх замовлень
//    @GetMapping("/manager/custom/get-all")
//    public List<Custom> getAllCustoms() {
//        return customService.getAllCustoms();
//    }

    //отримати список всіх замовлень зі статусом CREATED
    @GetMapping("/manager/custom/get-created")
    public List<Custom> getAllCreatedCustoms(){
        return customService.getAllCreatedCustoms();
    }

    //призначити конкретного робітника на виконання конкретного замовлення
    @PostMapping("/manager/custom/assign-employee/")
    public void assignEmployeeToCustom(@RequestParam int customId, @RequestParam int employeeId) {
        customService.assignEmployeeToCustom(customId, employeeId);
    }

    //отримати список всіх робітників
    @GetMapping("/manager/employee/get-all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    //додати нового робітника
    @PostMapping("/manager/employee/save")
    public void saveEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
    }

    //видалення робітника по id
    @DeleteMapping("/manager/employee/{employeeId}")
    public void deleteEmployeeById(@PathVariable int employeeId) {
        employeeService.deleteId(employeeId);
    }

    //отримати список всіх продуктів
    @GetMapping("/manager/product/get-all")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    //додати новий продукт
    @PostMapping("/manager/product/save")
    public Product save(@RequestBody Product product){
        return productService.save(product);
    }

    //отримати список усіх звітів, які чекають на відповідь менеджера
    @GetMapping("/manager/custom/get-waiting")
    public List<Report> getAllWaiting(){
        return reportService.getAllWaitingReports();
    }

    //прийняти звіт
    @PostMapping("/manager/custom/report/{reportId}/accept")
    public void setReportAccepted(@PathVariable int reportId) {
        reportService.setReportAccepted(reportId);
    }

    //відхилити звіт
    @PostMapping("/manager/custom/report{reportId}/reject")
    public void setReportRejected(@PathVariable int reportId) {
        reportService.setReportRejected(reportId);
    }
}
