package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerCustom;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeCustom;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Custom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // One-to-Many relation with CustomerCustom
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "custom")
    private List<CustomerCustom> customerCustomList = new ArrayList<>();

    // One-to-Many relation with CustomProduct
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "custom")
    List<CustomProduct> customProductList = new ArrayList<>();

    //One-to-Many relation with EmployeeCustom
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "custom")
    List<EmployeeCustom> employeeCustomList = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CustomProduct> getCustomProductList() {
        return customProductList;
    }

    public void setCustomProductList(List<CustomProduct> customProductList) {
        this.customProductList = customProductList;
    }

    public List<CustomerCustom> getCustomerCustomList() {
        return customerCustomList;
    }

    public void setCustomerCustomList(List<CustomerCustom> customerCustomList) {
        this.customerCustomList = customerCustomList;
    }

    public List<EmployeeCustom> getEmployeeCustomList() {
        return employeeCustomList;
    }

    public void setEmployeeCustomList(List<EmployeeCustom> employeeCustomList) {
        this.employeeCustomList = employeeCustomList;
    }
}
