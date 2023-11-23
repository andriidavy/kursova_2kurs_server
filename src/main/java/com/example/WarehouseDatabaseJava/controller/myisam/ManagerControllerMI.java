package com.example.WarehouseDatabaseJava.controller.myisam;

import com.example.WarehouseDatabaseJava.MyISAM.model.department.DepartmentMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.department.ManagerDepartmentMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.order.report.ReportMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyIsamService;
import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
import com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO;
import com.example.WarehouseDatabaseJava.dto.report.ReportDTO;
import com.example.WarehouseDatabaseJava.dto.users.EmployeeProfileDTO;
import com.example.WarehouseDatabaseJava.dto.users.ManagerProfileDTO;
import com.example.WarehouseDatabaseJava.MyISAM.model.order.CustomMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.product.ProductMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.product.ProductMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.manager.ManagerMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.manager.ManagerMyIsamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManagerControllerMI {
    @Autowired
    ManagerMyIsamService managerMyIsamService;
    @Autowired
    ProductMyIsamService productMyIsamService;
    @Autowired
    CustomMyIsamService customMyIsamService;
    @Autowired
    ReportMyIsamService reportMyIsamService;
    @Autowired
    DepartmentMyIsamService departmentMyIsamService;
    @Autowired
    ManagerDepartmentMyIsamService managerDepartmentMyIsamService;
    @Autowired
    EmployeeMyIsamService employeeMyIsamService;

    //MANAGER SIDE

    //метод для логіну TESTED
    @GetMapping("/mi/manager/login")
    public ManagerMyISAM loginManager(@RequestParam String email, @RequestParam String password) {
        return managerMyIsamService.loginManager(email, password);
    }

    //отримати профіль менеджера по його id TESTED
    @GetMapping("/mi/manager/get-manager-by-id")
    public ManagerProfileDTO getManagerProfile(@RequestParam int managerId) {
        return managerMyIsamService.getManagerProfile(managerId);
    }

    //PRODUCT SIDE

    // додати/оновити продукт (вертає id доданого/оновленого продукту) TESTED
    @PostMapping("/mi/manager/provide-product")
    public int provideProduct(@RequestParam String productName, @RequestParam int quantity, @RequestParam double price, @RequestParam String description) {
        return productMyIsamService.provideProduct(productName, quantity, price, description);
    }

    //зберегти опис для продукту TESTED
    @PostMapping("/mi/manager/save-desc-for-product")
    public void saveDescriptionForProduct(@RequestParam int productId, @RequestParam String description) {
        productMyIsamService.saveDescriptionToProduct(productId, description);
    }

    //отримати список продуктів TESTED
    @GetMapping("/mi/manager/get-all-product")
    public List<ProductMyISAM> getAllProducts() {
        return productMyIsamService.getAllProducts();
    }

    //CUSTOM SIDE

    //отримати список всіх замовлень TESTED
    @GetMapping("/mi/manager/get-all-customs")
    public List<CustomDTO> getAllCustoms() {
        return customMyIsamService.getAllCustoms();
    }

    //отримати список замовлень без призначеного робітника для конкретного менеджера TESTED
    @GetMapping("/mi/manager/get-customs-without-employee")
    public List<CustomDTO> getCustomsWithoutAssignEmployee(@RequestParam int managerId) {
        return customMyIsamService.getAllCustomsWithoutAssignEmployee(managerId);
    }

    //EMPLOYEE SIDE

    //зберегти робітника TESTED
    @PostMapping("/mi/manager/employee/insert")
    public EmployeeMyISAM insertEmployee(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password) {
        return employeeMyIsamService.insertEmployee(name, surname, email, password);
    }

    //видалити робітника за його id TESTED
    @DeleteMapping("/mi/manager/employee/delete-by-id")
    public void deleteEmployeeById(@RequestParam int employeeId) {
        employeeMyIsamService.deleteEmployeeById(employeeId);
    }

    //призначити робітника на замовлення TESTED
    @PostMapping("/mi/manager/assign-employee-to-custom")
    public void assignEmployeeToCustom(@RequestParam int employeeId, @RequestParam int customId) {
        customMyIsamService.assignEmployeeToCustom(employeeId, customId);
    }

    //отримати список всіх профілів робітників
    @GetMapping("/mi/manager/employee/profile/get-all")
    public List<EmployeeProfileDTO> getAllEmployeesProfileDTO() {
        return employeeMyIsamService.getAllEmployees();
    }

    //REPORT SIDE

    //прийняти звіт TESTED
    @PostMapping("/mi/manager/set-report-accepted")
    public void setReportAccepted(@RequestParam int reportId) {
        reportMyIsamService.setReportAccepted(reportId);
    }

    //відхилити звіт TESTED
    @PostMapping("/mi/manager/set-report-rejected")
    public void setReportRejected(@RequestParam int reportId) {
        reportMyIsamService.setReportRejected(reportId);
    }

    //отримати список звітів, які очікують відповідь, для менеджера TESTED
    @GetMapping("/mi/manager/get-waiting-reports")
    public List<ReportDTO> getAllWaitingReportsForManager(@RequestParam int managerId) {
        return reportMyIsamService.getAllWaitingReportsForManager(managerId);
    }

    //----------------------------------------admin--function------------------------------------------------------//

    //MANAGER SIDE

    //зберегти менеджера TESTED
    @PostMapping("/mi/manager/insert")
    public ManagerMyISAM insertManager(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password) {
        return managerMyIsamService.insertManager(name, surname, email, password);
    }

    //видалити менеджера за його id TESTED
    @DeleteMapping("/mi/manager/delete-manager-by-id")
    public void deleteManagerById(@RequestParam int managerId) {
        managerMyIsamService.deleteManagerById(managerId);
    }

    //отримати список профілів всіх менеджерів TESTED
    @GetMapping("/mi/manager/profile/get-all")
    public List<ManagerProfileDTO> getAllManagersProfileDTO() {
        return managerMyIsamService.getAllManagers();
    }

    //DEPARTMENT SIDE

    //додати відділ доставки TESTED
    @PostMapping("/mi/manager/insert-department")
    public void insertDepartment(@RequestParam String departName) {
        departmentMyIsamService.insertDepartment(departName);
    }

    //призначити менеджера на відділ TESTED
    @PostMapping("/mi/manager/assign-manager-to-department")
    public void assignManagerToDepartment(@RequestParam int managerId, @RequestParam int departmentId) {
        managerDepartmentMyIsamService.assignManagerToDepartment(managerId, departmentId);
    }

    //скасувати призначення менеджера на відділ TESTED
    @DeleteMapping("/mi/manager/remove-department-for-manager")
    public void removeDepartmentForManager(@RequestParam int managerId, @RequestParam int departmentId) {
        managerDepartmentMyIsamService.removeDepartmentForManager(managerId, departmentId);
    }

    //отримати список всіх відділів TESTED
    @GetMapping("/mi/manager/department/get-all")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentMyIsamService.getAllDepartments();
    }

    //отримати список всіх відділів призначених для менеджера TESTED
    @GetMapping("/mi/manager/get-departments-for-manager")
    public List<DepartmentDTO> getAllDepartmentsForManager(@RequestParam int managerId) {
        return departmentMyIsamService.getAllDepartmentsForManager(managerId);
    }

    //отримати список всіх відділів НЕ призначених для менеджера TESTED
    @GetMapping("/mi/manager/get-departments-without-manager")
    public List<DepartmentDTO> getAllDepartmentsWithoutManager(@RequestParam int managerId) {
        return departmentMyIsamService.getAllDepartmentsWithoutManager(managerId);
    }
}
