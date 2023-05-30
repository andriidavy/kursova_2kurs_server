package com.example.WarehouseDatabaseJava.model.users.manager.stage;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.users.manager.Manager;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String departmentName;

    private Department(){}

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    //зв'язок Many-to-Many з Manager
    @ManyToMany
    @JoinTable(name = "manager_department",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    private List<Manager> managerList;

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

    public List<Manager> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<Manager> managerList) {
        this.managerList = managerList;
    }

    public List<Custom> getCustomList() {
        return customList;
    }

    public void setCustomList(List<Custom> customList) {
        this.customList = customList;
    }
}
