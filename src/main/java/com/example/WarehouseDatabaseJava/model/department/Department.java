package com.example.WarehouseDatabaseJava.model.department;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(catalog = "warehouse_database_innodb", name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "department_name")
    private String departmentName;

    //зв'язок One-to-Many з ManagerDepartment
    @OneToMany(mappedBy = "department")
    private List<ManagerDepartment> managerDepartmentList = new ArrayList<>();

    // зв'язок One-to-Many з Custom
    @OneToMany(mappedBy = "department")
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
