package com.example.WarehouseDatabaseJava.controller;

import com.example.WarehouseDatabaseJava.model.order.CustomDTO;
import com.example.WarehouseDatabaseJava.model.order.CustomService;
import com.example.WarehouseDatabaseJava.model.order.report.ReportDTO;
import com.example.WarehouseDatabaseJava.model.order.report.ReportService;
import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductDTO;
import com.example.WarehouseDatabaseJava.model.product.ProductService;
import com.example.WarehouseDatabaseJava.model.product.category.ProductCategory;
import com.example.WarehouseDatabaseJava.model.product.category.ProductCategoryService;
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

import java.util.Date;
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
    @Autowired
    ProductCategoryService productCategoryService;

    //зберегти нового менеджера TESTED!
    @PostMapping("/manager/save")
    public Manager saveManager(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password) {
        return managerService.save(name, surname, email, password);
    }

    //видалення певного менеджера по його id TESTED
    @DeleteMapping("/manager/delete-manager-by-id")
    public void deleteManagerById(@RequestParam String managerId) {
        managerService.deleteManagerById(managerId);
    }

    //отримати список всіх менеджерів TESTED
    @GetMapping("/manager/get-all")
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    //метод для логіну
    @PostMapping("/manager/login")
    public Manager loginManager(@RequestParam String email, @RequestParam String password) {
        return managerService.loginManager(email, password);
    }

    //отримати список профілів всіх менеджерів TESTED
    @GetMapping("/manager/profile/get-all")
    public List<ManagerProfileDTO> getAllManagersProfileDTO() {
        return managerService.getAllManagersProfileDTO();
    }

    //отримати профіль менеджера по його id TESTED
    @GetMapping("/manager/get-manager-by-id")
    public ManagerProfileDTO getManagerProfile(@RequestParam String managerId) {
        return managerService.getManagerProfile(managerId);
    }

    //отримати список всіх замовлень TESTED
    @GetMapping("/manager/custom/get-all")
    public List<CustomDTO> getAllCustoms() {
        return customService.getAllCustoms();
    }

    //отримати список всіх замовлень для певної дати
    @GetMapping("/manager/custom/get-all-by-date")
    public List<CustomDTO> getAllCustomsByDate(Date creationTime) {
        return customService.getAllCustomsByDate(creationTime);
    }

    //отримати список всіх замовлень зі статусом CREATED TESTED
    @GetMapping("/manager/custom/get-created")
    public List<CustomDTO> getAllCreatedCustoms(@RequestParam String managerId) {
        return customService.getAllCreatedCustoms(managerId);
    }

    //призначити конкретного робітника на виконання конкретного замовлення TESTED
    @PostMapping("/manager/custom/assign-employee/")
    public void assignEmployeeToCustom(@RequestParam String customId, @RequestParam String employeeId) {
        customService.assignEmployeeToCustom(customId, employeeId);
    }

    //отримати список всіх профілів робітників TESTED

    @GetMapping("/manager/employee/profile/get-all")
    public List<EmployeeProfileDTO> getAllEmployeesProfile() {
        return employeeService.getAllEmployeesProfile();
    }

    //додати нового робітника TESTED!
    @PostMapping("/manager/employee/save")
    public Employee saveEmployee(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password) {
        return employeeService.save(name, surname, email, password);
    }

    //видалення робітника по id TESTED
    @DeleteMapping("/manager/employee/delete-employee-by-id")
    public void deleteEmployeeById(@RequestParam String employeeId) {
        employeeService.deleteEmployeeById(employeeId);
    }

    //отримати список всіх продуктів (DTO!)
    @GetMapping("/manager/product/get-all")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProductsDTO();
    }

    //пошук продукта по баркоду (DTO!)
    @GetMapping("/manager/product/search-by-barcode")
    public ProductDTO searchProductByBarcode(@RequestParam String searchBarcode) {
        return productService.searchProductByBarcode(searchBarcode);
    }

    //перевірити наявність продукту по barcode
    @GetMapping("/manager/product/check-barcode")
    public boolean checkBarcode(long barcode) {
        return productService.checkBarcode(barcode);
    }

    //додати новий продукт TESTED!
    @PostMapping("/manager/product/save")
    public Product saveProduct(@RequestParam long barcode, @RequestParam String name, @RequestParam double price, @RequestParam String description, @RequestParam int quantity) {
        return productService.saveProduct(barcode, name, price, description, quantity);
    }

    //збільшити кількість наявного продукту
    @PostMapping("/manager/product/add-count")
    public Product addProductQuantity(@RequestParam long barcode, @RequestParam int quantity) {
        return productService.addProductQuantity(barcode, quantity);
    }

    //отримати список усіх звітів, які чекають на відповідь менеджера TESTED
    //+ враховує відділи для замовлень
    @GetMapping("/manager/custom/report/get-waiting")
    public List<ReportDTO> getAllWaiting(@RequestParam String managerId) {
        return reportService.getAllWaitingReportsForManager(managerId);
    }

    //прийняти звіт TESTED
    @PostMapping("/manager/custom/report/accept")
    public void setReportAccepted(@RequestParam String reportId) {
        reportService.setReportAccepted(reportId);
    }

    //відхилити звіт TESTED
    @PostMapping("/manager/custom/report/reject")
    public void setReportRejected(@RequestParam String reportId) {
        reportService.setReportRejected(reportId);
    }

    //додати зворотнє повідомлення для відхиленого звіту
    @PostMapping("/manager/custom/report/set-callback")
    public void setReportCallback(@RequestParam String reportId, @RequestParam String callbackText) {
        reportService.setReportCallback(reportId, callbackText);
    }

    //зберегти відділ TESTED
    @PostMapping("/manager/department/save")
    public Department saveDepartment(@RequestParam String departmentName) {
        return departmentService.save(departmentName);
    }

    //видалити відділ по id TESTED
    @DeleteMapping("/manager/department/delete-by-id")
    public void removeDepartmentById(@RequestParam String departmentId) {
        departmentService.removeDepartmentById(departmentId);
    }

    //отримати список всіх відділів TESTED
    @GetMapping("/manager/department/get-all")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    //призначити відділ на менеджера TESTED
    @PostMapping("/manager/department/assign-department-to-manager")
    public void assignDepartmentToManager(@RequestParam String managerId, @RequestParam String departmentId) {
        managerService.assignDepartmentToManager(managerId, departmentId);
    }

    //прибрати призначений відділ у менеджера TESTED
    @PostMapping("/manager/department/remove-department-from-manager")
    public void removeDepartmentFromManager(@RequestParam String managerId, @RequestParam String departmentId) {
        managerService.removeDepartmentFromManager(managerId, departmentId);
    }

    //метод отримання всіх етапів, на які призначений певний менеджер TESTED
    @GetMapping("/manager/department/get-departments-for-manager")
    public List<DepartmentDTO> getAllDepartmentsForManager(@RequestParam String managerId) {
        return managerService.getAllDepartmentsForManager(managerId);
    }

    //метод отримання всіх етапів, на які певний менеджер НЕ призначений TESTED
    @GetMapping("/manager/department/get-departments-non-for-manager")
    public List<DepartmentDTO> getDepartmentsWithoutManager(@RequestParam String managerId) {
        return managerService.getDepartmentsWithoutManager(managerId);
    }

    //метод отримання всіх менеджерів, призначених на певний етап TESTED
    @GetMapping("/manager/department/get-managers-for-department")
    public List<ManagerProfileDTO> getAllManagersForDepartment(@RequestParam String departmentId) {
        return departmentService.getAllManagersForDepartment(departmentId);
    }

    //метод створення нової категорії товарів TESTED!
    @PostMapping("/manager/category-product/create")
    public ProductCategory createProductCategory(@RequestParam String categoryName) {
        return productCategoryService.createProductCategory(categoryName);
    }

    //метод видалення категорії товарів
    @DeleteMapping("/manager/category-product/delete")
    public void removeProductCategoryById(@RequestParam String categoryId) {
        productCategoryService.removeProductCategoryById(categoryId);
    }

    //метод призначення певного товару на певну категорію TESTED!
    @PostMapping("/manager/category-product/assign-to-product")
    public void assignProductToCategory(@RequestParam String productId, @RequestParam String categoryId) {
        productCategoryService.assignProductToCategory(productId, categoryId);
    }

    //метод для додавання та оновлення url зображення для продукту
    @PostMapping("/manager/product/set-image")
    public void addImageUrlToProduct(@RequestParam String productId, @RequestParam String url) {
        productService.addImageUrlToProduct(productId, url);
    }

    // метод для видалення url зображення для продукту
    @PostMapping("/manager/product/delete-image")
    public void deleteImageForProduct(@RequestParam String productId) {
        productService.deleteImageForProduct(productId);
    }

}
