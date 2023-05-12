package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.order.report.Report;
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

    //One-to-One relation with Report
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "custom")
    Report report;

    public enum Status{
        CREATED(1),
        IN_PROCESSING(2),
        PROCESSED(3),
        SENT(4);
        private final int value;
        Status(int value){
            this.value=value;
        }

        public int getValue() {
            return value;
        }
    }

    //enumerated вказує на те як буде відображатися значення статусу, в данному випадку це буде рядкове значення
    @Enumerated(EnumType.STRING)
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
