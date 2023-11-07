package com.example.WarehouseDatabaseJava.controller.myisam;

import com.example.WarehouseDatabaseJava.MyISAM.model.department.DepartmentMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.department.ManagerDepartmentMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.order.report.ReportMyIsamService;
import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
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

    //зберегти менеджера TESTED
    @PostMapping("/mi/manager/insert")
    public ManagerMyISAM insertManager(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password) {
        return managerMyIsamService.insertManager(name, surname, email, password);
    }

    //метод для логіну
    @GetMapping("/mi/manager/login")
    public ManagerMyISAM loginManager(@RequestParam String email, @RequestParam String password) {
        return managerMyIsamService.loginManager(email, password);
    }

    //отримати профіль менеджера по його id
    @GetMapping("/mi/manager/get-manager-by-id")
    public ManagerProfileDTO getManagerProfile(@RequestParam int managerId) {
        return managerMyIsamService.getManagerProfile(managerId);
    }

    // додати/оновити продукт (вертає id доданого/оновленого продукту)
    @PostMapping("/mi/manager/provide-product")
    public int provideProduct(@RequestParam String productName, @RequestParam int quantity) {
        return productMyIsamService.provideProduct(productName, quantity);
    }

    //отримати список продуктів
    @GetMapping("/mi/manager/get-all-product")
    public List<ProductMyISAM> getAllProducts() {
        return productMyIsamService.getAllProducts();
    }

    //зберегти опис для продукту
    @PostMapping("/mi/manager/save-desc-for-product")
    public void saveDescriptionForProduct(@RequestParam int productId, @RequestParam String description) {
        productMyIsamService.saveDescriptionToProduct(productId, description);
    }

    //отримати список всіх замовлень
    @GetMapping("/mi/manager/get-all-customs")
    public List<CustomDTO> getAllCustoms() {
        return customMyIsamService.getAllCustoms();
    }

    //призначити робітника на замовлення
    @PostMapping("/mi/manager/assign-employee-to-custom")
    public void assignEmployeeToCustom(@RequestParam int employeeId, @RequestParam int customId) {
        customMyIsamService.assignEmployeeToCustom(employeeId, customId);
    }

    //прийняти звіт
    @PostMapping("/mi/manager/set-report-accepted")
    public void setReportAccepted(@RequestParam int reportId) {
        reportMyIsamService.setReportAccepted(reportId);
    }

    //відхилити звіт
    @PostMapping("/mi/manager/set-report-rejected")
    public void setReportRejected(@RequestParam int reportId) {
        reportMyIsamService.setReportRejected(reportId);
    }


    //----------------------------------------admin--function------------------------------------------------------//

    //додати відділ доставки
    @PostMapping("/mi/manager/insert-department")
    public void insertDepartment(@RequestParam String departName) {
        departmentMyIsamService.insertDepartment(departName);
    }

    //призначити менеджера на відділ
    @PostMapping("/mi/manager/assign-manager-to-department")
    public void assignManagerToDepartment(@RequestParam int managerId, @RequestParam int departmentId) {
        managerDepartmentMyIsamService.assignManagerToDepartment(managerId, departmentId);
    }

    //скасувати призначення менеджера на відділ
    @DeleteMapping("/mi/manager/remove-department-for-manager")
    public void removeDepartmentForManager(@RequestParam int managerId, @RequestParam int departmentId) {
        managerDepartmentMyIsamService.removeDepartmentForManager(managerId, departmentId);
    }

}
