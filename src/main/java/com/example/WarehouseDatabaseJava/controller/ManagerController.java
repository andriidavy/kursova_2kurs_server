package com.example.WarehouseDatabaseJava.controller;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.order.CustomDTO;
import com.example.WarehouseDatabaseJava.model.order.CustomProductDTO;
import com.example.WarehouseDatabaseJava.model.order.CustomService;
import com.example.WarehouseDatabaseJava.model.order.report.Report;
import com.example.WarehouseDatabaseJava.model.order.report.ReportDTO;
import com.example.WarehouseDatabaseJava.model.order.report.ReportService;
import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductService;
import com.example.WarehouseDatabaseJava.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeProfileDTO;
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

    //зберегти нового менеджера TESTED
    @PostMapping("/manager/save")
    public Manager save(@RequestBody Manager manager) {
        return managerService.save(manager);
    }

    //отримати список всіх менеджерів TESTED
    @GetMapping("/manager/get-all")
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping("/manager/custom/get-all")
    public List<CustomDTO> getAllCustoms() {
        return customService.getAllCustoms();
    }

    //отримати список всіх замовлень зі статусом CREATED TESTED
    @GetMapping("/manager/custom/get-created")
    public List<CustomDTO> getAllCreatedCustoms() {
        return customService.getAllCreatedCustoms();
    }

    //призначити конкретного робітника на виконання конкретного замовлення TESTED
    @PostMapping("/manager/custom/assign-employee/")
    public void assignEmployeeToCustom(@RequestParam int customId, @RequestParam int employeeId) {
        customService.assignEmployeeToCustom(customId, employeeId);
    }

    //отримати список всіх профілів робітників TESTED

    @GetMapping("/manager/employee/profile/get-all")
    public List<EmployeeProfileDTO> getAllEmployeesProfile() {
        return employeeService.getAllEmployeesProfile();
    }

    //додати нового робітника TESTED
    @PostMapping("/manager/employee/save")
    public void saveEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
    }

    //видалення робітника по id TESTED
    @DeleteMapping("/manager/employee/delete-employee-by-id")
    public void deleteEmployeeById(@RequestParam int employeeId) {
        employeeService.deleteId(employeeId);
    }

    //отримати список всіх продуктів TESTED
    @GetMapping("/manager/product/get-all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //додати новий продукт TESTED
    @PostMapping("/manager/product/save")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    //отримати список усіх звітів, які чекають на відповідь менеджера TESTED
    @GetMapping("/manager/custom/get-waiting")
    public List<ReportDTO> getAllWaiting() {
        return reportService.getAllWaitingReports();
    }

    //прийняти звіт TESTED
    @PostMapping("/manager/custom/report/accept")
    public void setReportAccepted(@RequestParam int reportId) {
        reportService.setReportAccepted(reportId);
    }

    //відхилити звіт TESTED
    @PostMapping("/manager/custom/report/reject")
    public void setReportRejected(@RequestParam int reportId) {
        reportService.setReportRejected(reportId);
    }
}
