package com.example.WarehouseDatabaseJava.InnoDB.model.users.manager;

import com.example.WarehouseDatabaseJava.InnoDB.model.department.ManagerDepartment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(catalog = "warehouse_database_innodb")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;

    //зв'зок багато до багатьох з етапом
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manager")
    @JsonIgnore
    private List<ManagerDepartment> managerDepartmentList = new ArrayList<>();

    public Manager() {
    }

    public Manager(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ManagerDepartment> getManagerDepartmentList() {
        return managerDepartmentList;
    }

    public void setManagerDepartmentList(List<ManagerDepartment> managerDepartmentList) {
        this.managerDepartmentList = managerDepartmentList;
    }
}
