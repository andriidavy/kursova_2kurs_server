package com.example.WarehouseDatabaseJava.MyISAM.model.department;

import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_myisam", name = "manager_department_myisam")
public class ManagerDepartmentMyISAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "department_id")
    private int departmentId;
    @Column(name = "manager_id")
    private int managerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
