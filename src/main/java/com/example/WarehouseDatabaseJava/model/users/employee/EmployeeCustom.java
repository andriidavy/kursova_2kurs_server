package com.example.WarehouseDatabaseJava.model.users.employee;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import jakarta.persistence.*;

@Entity
public class EmployeeCustom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Many-to-One relation with Employee
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    //Many-to-One relation with Custom

    @ManyToOne
    @JoinColumn(name = "custom_id")
    private Custom custom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Custom getCustom() {
        return custom;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }
}
