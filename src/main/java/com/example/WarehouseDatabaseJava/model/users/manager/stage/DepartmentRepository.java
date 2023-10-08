package com.example.WarehouseDatabaseJava.model.users.manager.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByDepartmentName(String departmentName);
    @Query("SELECT md.department FROM ManagerDepartment md WHERE md.manager.id = :managerId")
    List<Department> findAllByManagerId(@Param("managerId") int managerId);

    @Query("SELECT md.department FROM ManagerDepartment md WHERE md.manager.id != :managerId")
    List<Department> findAllByNotManagerId(@Param("managerId") int managerId);
}
