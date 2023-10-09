package com.example.WarehouseDatabaseJava.MyISAM.model.order;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_myisam")
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
    private Custom.Status status;

    private int customerId;
    private int departmentId;
    private int employeeId;

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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
