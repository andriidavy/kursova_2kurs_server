package com.example.WarehouseDatabaseJava.MyISAM.model.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMyIsamRepository extends JpaRepository<DepartmentMyISAM, Integer> {
    @Query(value = "SELECT department_name FROM department_myisam AS d JOIN manager_department_myisam AS md ON d.id = md.department_id WHERE md.manager_id = :manager_id", nativeQuery = true)
    List<String> findAllDepartmentsNameByManagerId(@Param("manager_id") int managerId);

    @Query(value = "SELECT department_name FROM department_myisam AS d WHERE d.id = :department_id", nativeQuery = true)
    String getDepartmentNameById(@Param("department_id") int departmentId);

    @Query(value = "INSERT INTO department_myisam (department_name) VALUES (:depart_name)", nativeQuery = true)
    @Modifying
    void insertDepartment(@Param("depart_name") String name);
}
