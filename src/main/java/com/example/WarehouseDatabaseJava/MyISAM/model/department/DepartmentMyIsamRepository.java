package com.example.WarehouseDatabaseJava.MyISAM.model.department;

import com.example.WarehouseDatabaseJava.InnoDB.model.department.Department;
import com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMyIsamRepository extends JpaRepository<DepartmentMyISAM, Integer> {
    @Query(value = "INSERT INTO department_myisam (department_name) VALUES (:depart_name)", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void insertDepartment(@Param("depart_name") String name);

    @Query(value = "SELECT department_name FROM department_myisam AS d JOIN manager_department_myisam AS md ON d.id = md.department_id WHERE md.manager_id = :manager_id", nativeQuery = true)
    List<String> findAllDepartmentsNameByManagerId(@Param("manager_id") int managerId);

    @Query(value = "SELECT department_name FROM department_myisam AS d WHERE d.id = :department_id", nativeQuery = true)
    String getDepartmentNameById(@Param("department_id") int departmentId);

    @Query(value = "SELECT * FROM department_myisam", nativeQuery = true)
    List<DepartmentMyISAM> getAllDepartments();

    @Query(value = "SELECT d.id, d.department_name FROM department_myisam AS d JOIN manager_department_myisam md ON d.id = md.department_id WHERE md.manager_id = :manager_id", nativeQuery = true)
    List<DepartmentMyISAM> getAllDepartmentsForManager(@Param("manager_id") int managerId);

    @Query(value = "SELECT d.id, d.department_name FROM department_myisam AS d WHERE d.id NOT IN (SELECT md.department_id FROM manager_department_myisam AS md WHERE md.manager_id = :manager_id) ORDER BY d.id", nativeQuery = true)
    List<DepartmentMyISAM> getAllDepartmentsWithoutManager(@Param("manager_id") int managerId);
}
