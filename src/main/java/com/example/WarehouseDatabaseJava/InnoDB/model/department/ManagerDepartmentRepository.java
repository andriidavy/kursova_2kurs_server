package com.example.WarehouseDatabaseJava.InnoDB.model.department;

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
public interface ManagerDepartmentRepository extends JpaRepository<ManagerDepartment, Integer> {
    @Query(value = "INSERT INTO manager_department (manager_id, department_id) VALUES (:m_id, :d_id)", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void assignManagerToDepartment(@Param("m_id") int managerId, @Param("d_id") int departmentId);

    @Query(value = "DELETE FROM manager_department AS md WHERE md.manager_id = :m_id AND md.department_id = :d_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void removeDepartmentForManager(@Param("m_id") int managerId, @Param("d_id") int departmentId);
}
