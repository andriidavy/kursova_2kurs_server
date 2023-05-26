package com.example.WarehouseDatabaseJava.model.users.manager.stage;

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

    //зв'язок Many-to-Many з Manager
    @ManyToMany
    @JoinTable(name = "manager_department",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    private List<Manager> managerList;

    // зв'язок One-to-Many з DepartmentCustom
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private List<DepartmentCustom> departmentCustomList = new ArrayList<>();

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

    public List<DepartmentCustom> getDepartmentCustomList() {
        return departmentCustomList;
    }

    public void setDepartmentCustomList(List<DepartmentCustom> departmentCustomList) {
        this.departmentCustomList = departmentCustomList;
    }
}
