package com.example.WarehouseDatabaseJava.MyISAM.model.department;

import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_myisam", name = "department_myisam")
public class DepartmentMyISAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String departmentName;

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
}
