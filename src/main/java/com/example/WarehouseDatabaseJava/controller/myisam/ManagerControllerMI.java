package com.example.WarehouseDatabaseJava.controller.myisam;

import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
import com.example.WarehouseDatabaseJava.dto.users.ManagerProfileDTO;
import com.example.WarehouseDatabaseJava.MyISAM.model.order.CustomMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.product.ProductMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.product.ProductMyIsamService;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.manager.ManagerMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.manager.ManagerMyIsamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManagerControllerMI {
    @Autowired
    ManagerMyIsamService managerMyIsamService;
    @Autowired
    ProductMyIsamService productMyIsamService;
    @Autowired
    CustomMyIsamService customMyIsamService;

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
}
