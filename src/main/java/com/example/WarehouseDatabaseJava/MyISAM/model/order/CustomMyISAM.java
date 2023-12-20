package com.example.WarehouseDatabaseJava.MyISAM.model.order;

import com.example.WarehouseDatabaseJava.InnoDB.model.order.Custom;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(catalog = "warehouse_database_myisam", name = "custom_myisam")
public class CustomMyISAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public enum Status{
        CREATED(1),
        IN_PROCESSING(2),
        WAITING_RESPONSE(3),
        PROCESSED(4),
        SENT(5);
        private final int value;
        Status(int value){
            this.value=value;
        }

        public int getValue() {
            return value;
        }
    }

    //enumerated вказує на те як буде відображатися значення статусу, в данному випадку це буде рядкове значення
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Custom.Status status;
    @Column(name = "price")
    private double price;
    @Column(name = "customer_id")
    private int customerId;
    @Column(name = "department_id")
    private int departmentId;
    @Column(name = "employee_id")
    private Integer employeeId;
    @Column(name = "creation_date")
    private LocalDate creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Custom.Status getStatus() {
        return status;
    }

    public void setStatus(Custom.Status status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
