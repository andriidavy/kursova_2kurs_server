package com.example.WarehouseDatabaseJava.model.users.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;

    //One-to-Many relation with EmployeeCustom
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    @JsonIgnore
    private List<EmployeeCustom> employeeCustomList = new ArrayList<>();

    public Employee() {
    }

    public Employee(String name, String surname, String email, String password) {
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public List<EmployeeCustom> getEmployeeCustomList() {
        return employeeCustomList;
    }

    public void setEmployeeCustomList(List<EmployeeCustom> employeeCustomList) {
        this.employeeCustomList = employeeCustomList;
    }
}
