package com.example.WarehouseDatabaseJava.model.department;

import com.example.WarehouseDatabaseJava.model.users.manager.Manager;
import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_innodb", name = "manager_department")
public class ManagerDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
