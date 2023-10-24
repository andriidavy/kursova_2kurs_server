package com.example.WarehouseDatabaseJava.InnoDB.model.users.manager.stage;

import com.example.WarehouseDatabaseJava.InnoDB.model.order.Custom;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(catalog = "warehouse_database_innodb")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String departmentName;

    public Department() {
    }

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    //зв'язок One-to-Many з ManagerDepartment
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private List<ManagerDepartment> managerDepartmentList = new ArrayList<>();

    // зв'язок One-to-Many з Custom
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private List<Custom> customList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<ManagerDepartment> getManagerDepartmentList() {
        return managerDepartmentList;
    }

    public void setManagerDepartmentList(List<ManagerDepartment> managerDepartmentList) {
        this.managerDepartmentList = managerDepartmentList;
    }

    public List<Custom> getCustomList() {
        return customList;
    }

    public void setCustomList(List<Custom> customList) {
        this.customList = customList;
    }
}
