package com.example.WarehouseDatabaseJava.model.users.manager.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByDepartmentName(String departmentName);
}
