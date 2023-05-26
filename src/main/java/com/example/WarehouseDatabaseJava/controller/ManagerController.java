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
import com.example.WarehouseDatabaseJava.model.users.manager.ManagerProfileDTO;
import com.example.WarehouseDatabaseJava.model.users.manager.ManagerService;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.Department;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentDTO;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentService;
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
    @Autowired
    DepartmentService departmentService;

    //зберегти нового менеджера TESTED
    @PostMapping("/manager/save")
    public Manager save(@RequestBody Manager manager) {
        return managerService.save(manager);
    }

    //видалення певного менеджера по його id TESTED
    @DeleteMapping("/manager/delete-manager-by-id")
    public void deleteManagerById(@RequestParam int managerId) {
        managerService.deleteManagerById(managerId);
    }

    //отримати список всіх менеджерів TESTED
    @GetMapping("/manager/get-all")
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    //отримати список профілів всіх менеджерів TESTED
    @GetMapping("/manager/profile/get-all")
    public List<ManagerProfileDTO> getAllManagersProfileDTO(){
        return managerService.getAllManagersProfileDTO();
    }

    //отримати профіль менеджера по його id TESTED
    @GetMapping("/manager/get-manager-by-id")
    public ManagerProfileDTO getManagerProfile(@RequestParam int managerId) {
        return managerService.getManagerProfile(managerId);
    }

    //отримати список всіх замовлень TESTED
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
    public Product saveProduct(@RequestBody Product product) {
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

    //зберегти відділ TESTED
    @PostMapping("/manager/department/save")
    public Department save(@RequestBody Department department) {
        return departmentService.save(department);
    }

    //видалити відділ по id TESTED
    @DeleteMapping("/manager/department/delete-by-id")
    public void removeDepartmentById(@RequestParam int departmentId) {
        departmentService.removeDepartmentById(departmentId);
    }

    //отримати список всіх відділів TESTED
    @GetMapping("/manager/department/get-all")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    //призначити відділ на менеджера TESTED
    @PostMapping("/manager/department/assign-department-to-manager")
    public void assignDepartmentToManager(@RequestParam int managerId, @RequestParam int departmentId) {
        managerService.assignDepartmentToManager(managerId, departmentId);
    }

    //прибрати призначений відділ у менеджера TESTED
    @PostMapping("/manager/department/remove-department-from-manager")
    public void removeDepartmentFromManager(@RequestParam int managerId, @RequestParam int departmentId) {
        managerService.removeDepartmentFromManager(managerId, departmentId);
    }

    //метод отримання всіх етапів, на які призначений певний менеджер TESTED
    @GetMapping("/manager/department/get-departments-for-manager")
    public List<DepartmentDTO> getAllDepartmentsForManager(@RequestParam int managerId) {
        return managerService.getAllDepartmentsForManager(managerId);
    }

    //метод отримання всіх етапів, на які певний менеджер НЕ призначений TESTED
    @GetMapping("/manager/department/get-departments-non-for-manager")
    public List<DepartmentDTO> getDepartmentsWithoutManager(@RequestParam int managerId){
        return managerService.getDepartmentsWithoutManager(managerId);
    }

    //метод отримання всіх менеджерів, призначених на певний етап TESTED
    @GetMapping("/manager/department/get-managers-for-department")
    public List<ManagerProfileDTO> getAllManagersForDepartment(@RequestParam int departmentId) {
        return departmentService.getAllManagersForDepartment(departmentId);
    }
}
