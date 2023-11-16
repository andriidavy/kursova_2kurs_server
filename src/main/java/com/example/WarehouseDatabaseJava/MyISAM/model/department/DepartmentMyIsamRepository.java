package com.example.WarehouseDatabaseJava.MyISAM.model.department;

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

    @Query(value = "SELECT new com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO (d.id, d.departmentName) FROM DepartmentMyISAM d")
    List<DepartmentDTO> getAllDepartments();

    @Query(value = "SELECT new com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO (d.id, d.departmentName) FROM DepartmentMyISAM d JOIN ManagerDepartmentMyISAM md ON d.id = md.departmentId WHERE md.managerId = :manager_id")
    List<DepartmentDTO> getAllDepartmentsForManager(@Param("manager_id") int managerId);

    @Query(value = "SELECT new com.example.WarehouseDatabaseJava.dto.department.DepartmentDTO (d.id, d.departmentName) FROM DepartmentMyISAM d LEFT JOIN ManagerDepartmentMyISAM md ON d.id = md.departmentId WHERE md.managerId != :manager_id OR md.managerId IS NULL")
    List<DepartmentDTO> getAllDepartmentsWithoutManager(@Param("manager_id") int managerId);
}
