package com.example.WarehouseDatabaseJava.model.users.manager.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerDepartmentRepository extends JpaRepository<ManagerDepartment, Integer> {
    Boolean existsByManagerIdAndDepartmentId(int managerId, int departmentId);
    ManagerDepartment getReferenceByManagerIdAndDepartmentId(int managerId, int departmentId);
    List<ManagerDepartment> findAllByManagerId(int managerId);
}
