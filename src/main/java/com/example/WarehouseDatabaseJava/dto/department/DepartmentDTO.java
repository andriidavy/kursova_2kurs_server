package com.example.WarehouseDatabaseJava.dto.department;

public class DepartmentDTO {
    private int id;

    private String departmentName;

    public DepartmentDTO() {
    }

    public DepartmentDTO(int id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

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
