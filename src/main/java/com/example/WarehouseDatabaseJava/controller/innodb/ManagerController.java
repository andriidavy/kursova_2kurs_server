package com.example.WarehouseDatabaseJava.controller.innodb;

import com.example.WarehouseDatabaseJava.InnoDB.model.department.DepartmentService;
import com.example.WarehouseDatabaseJava.InnoDB.model.department.ManagerDepartmentService;
import com.example.WarehouseDatabaseJava.InnoDB.model.order.CustomService;
import com.example.WarehouseDatabaseJava.InnoDB.model.order.report.ReportService;
import com.example.WarehouseDatabaseJava.InnoDB.model.product.ProductService;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.employee.EmployeeService;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.manager.ManagerService;
import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
import com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO;
import com.example.WarehouseDatabaseJava.dto.product.ProductDTO;
import com.example.WarehouseDatabaseJava.dto.report.ReportDTO;
import com.example.WarehouseDatabaseJava.dto.users.EmployeeProfileDTO;
import com.example.WarehouseDatabaseJava.dto.users.ManagerProfileDTO;
import com.example.WarehouseDatabaseJava.dto.users.staff.StaffDTO;
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
    private ManagerDepartmentService managerDepartmentService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private DepartmentService departmentService;

    // MANAGER SIDE

    //метод для логіну TESTED
    @GetMapping("/manager/login")
    public int loginManager(String email, String password) {
        return managerService.loginManager(email, password);
    }

    //отримати профіль менеджера по його id TESTED
    @GetMapping("/manager/get-manager-by-id")
    public ManagerProfileDTO getManagerProfile(@RequestParam int managerId) {
        return managerService.getManagerProfile(managerId);
    }

    // PRODUCT SIDE

    // додати/оновити продукт (вертає id доданого/оновленого продукту) TESTED
    @PostMapping("/manager/provide-product")
    public int provideProduct(@RequestParam String productName, @RequestParam int quantity, @RequestParam double price, @RequestParam String description) {
        return productService.provideProduct(productName, quantity, price, description);
    }

    //перевірити на наявність продукту з таким же найменуванням TESTED
    @GetMapping("/manager/is-product-exist")
    public Boolean isProductExists(@RequestParam String productName) {
        return productService.isProductExistByName(productName);
    }

    //зберегти опис для продукту NOT USING
    @PostMapping("/manager/save-desc-for-product")
    public void saveDescriptionForProduct(@RequestParam int productId, @RequestParam String description) {
        productService.saveDescriptionToProduct(productId, description);
    }

    //отримати список всіх продуктів TESTED
    @GetMapping("/manager/product/get-all")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    //отримати продукт по його ID TESTED
    @GetMapping("/manager/search-product-by-id")
    public ProductDTO searchProductById(@RequestParam int productId) {
        return productService.searchProductById(productId);
    }

    //CUSTOM SIDE

    //отримати список всіх замовлень TESTED
    @GetMapping("/manager/custom/get-all")
    public List<CustomDTO> getAllCustoms() {
        return customService.getAllCustoms();
    }

    //отримати список замовлень без призначеного робітника для конкретного менеджера TESTED
    @GetMapping("/manager/get-customs-without-employee")
    public List<CustomDTO> getCustomsWithoutAssignEmployee(@RequestParam int managerId) {
        return customService.getAllCustomsWithoutAssignEmployee(managerId);
    }

    //отримати замовлення по його ID TESTED
    @GetMapping("/manager/search-custom-by-id")
    public CustomDTO searchCustomById(@RequestParam int customId) {
        return customService.searchCustomById(customId);
    }

    //EMPLOYEE SIDE

    //додати нового робітника TESTED
    @PostMapping("/manager/employee/insert")
    public int insertEmployee(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password, @RequestParam String repPassword) {
        return employeeService.insertEmployee(name, surname, email, password, repPassword);
    }

    //видалення робітника по id TESTED
    @DeleteMapping("/manager/employee/delete-employee-by-id")
    public void deleteEmployeeById(@RequestParam int employeeId) {
        employeeService.deleteEmployeeById(employeeId);
    }

    //призначити конкретного робітника на виконання конкретного замовлення TESTED
    @PostMapping("/manager/custom/assign-employee")
    public void assignEmployeeToCustom(@RequestParam int customId, @RequestParam int employeeId) {
        customService.assignEmployeeToCustom(customId, employeeId);
    }

    //отримати список всіх профілів робітників TESTED
    @GetMapping("/manager/employee/profile/get-all")
    public List<EmployeeProfileDTO> getAllEmployeesProfileDTO() {
        return employeeService.getAllEmployees();
    }

    //отримати весь список персоналу
    @GetMapping("/manager/get-staff")
    public List<StaffDTO> getStaff() {
        return managerService.getStaffDTO();
    }

    //REPORT SIDE

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

    //отримати список усіх звітів, які чекають на відповідь менеджера TESTED
    @GetMapping("/manager/custom/report/get-waiting")
    public List<ReportDTO> getAllWaitingForManager(@RequestParam int managerId) {
        return reportService.getAllWaitingReportsForManager(managerId);
    }

    //----------------------------------------admin--function------------------------------------------------------//

    //MANAGER SIDE

    //зберегти нового менеджера TESTED
    @PostMapping("/manager/insert")
    public int insertManager(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password, @RequestParam String repPassword) {
        return managerService.insertManager(name, surname, email, password, repPassword);
    }

    //видалення певного менеджера по його id TESTED
    @DeleteMapping("/manager/delete-manager-by-id")
    public void deleteManagerById(@RequestParam int managerId) {
        managerService.deleteManagerById(managerId);
    }

    //отримати список профілів всіх менеджерів TESTED
    @GetMapping("/manager/profile/get-all")
    public List<ManagerProfileDTO> getAllManagersProfileDTO() {
        return managerService.getAllManagers();
    }

    //DEPARTMENT SIDE

    //зберегти відділ TESTED
    @PostMapping("/manager/department/save")
    public void saveDepartment(@RequestParam String departmentName) {
        departmentService.insertDepartment(departmentName);
    }

    //призначити відділ на менеджера TESTED
    @PostMapping("/manager/department/assign-department-to-manager")
    public void assignDepartmentToManager(@RequestParam int managerId, @RequestParam int departmentId) {
        managerDepartmentService.assignManagerToDepartment(managerId, departmentId);
    }

    //прибрати призначений відділ у менеджера TESTED
    @DeleteMapping("/manager/department/remove-department-from-manager")
    public void removeDepartmentFromManager(@RequestParam int managerId, @RequestParam int departmentId) {
        managerDepartmentService.removeDepartmentForManager(managerId, departmentId);
    }

    //отримати список всіх відділів TESTED
    @GetMapping("/manager/department/get-all")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    //метод отримання всіх етапів, на які призначений певний менеджер TESTED
    @GetMapping("/manager/department/get-departments-for-manager")
    public List<DepartmentDTO> getAllDepartmentsForManager(@RequestParam int managerId) {
        return departmentService.getAllDepartmentsForManager(managerId);
    }

    //метод отримання всіх етапів, на які певний менеджер НЕ призначений TESTED
    @GetMapping("/manager/department/get-departments-non-for-manager")
    public List<DepartmentDTO> getDepartmentsWithoutManager(@RequestParam int managerId) {
        return departmentService.getAllDepartmentsWithoutManager(managerId);
    }
}
