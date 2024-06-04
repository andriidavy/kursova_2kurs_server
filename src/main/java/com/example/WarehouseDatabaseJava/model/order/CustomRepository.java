package com.example.WarehouseDatabaseJava.model.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRepository extends JpaRepository<Custom, Integer> {
    @Procedure("_create_custom")
    @Modifying
    int createCustom(@Param("new_customer_id") int customerId, @Param("new_department_id") int departmentId);

    @Procedure("_assign_employee_to_custom")
    @Modifying
    void assignEmployeeToCustom(@Param("new_custom_id") int customId, @Param("new_employee_id") int employeeId);

    @Procedure("_set_custom_sent")
    @Modifying
    void setCustomSent(@Param("custom_id") int customId);

    @Query(value = "SELECT * FROM custom", nativeQuery = true)
    List<Custom> getAllCustoms();

    @Query(value = "SELECT * FROM custom", nativeQuery = true)
    List<Custom> getAllCustomsPage(Pageable pageable);

    @Query(value = "SELECT * FROM custom AS c WHERE c.customer_id = :customer_id", nativeQuery = true)
    List<Custom> getCustomsForCustomer(@Param("customer_id") int customerId);

    @Query(value = "SELECT c.id, c.status, c.customer_id, c.department_id, c.employee_id, c.price, c.creation_date FROM custom AS c JOIN manager_department AS md ON c.department_id = md.department_id WHERE c.employee_id IS NULL AND md.manager_id = :manager_id AND (c.status = 'CREATED' OR c.status = 'IN_PROCESSING' OR c.status = 'PROCESSED') ORDER BY c.creation_date ASC", nativeQuery = true)
    List<Custom> getAllCustomsWithoutAssignEmployee(@Param("manager_id") int managerId);

    @Query(value = "SELECT * FROM custom AS c WHERE c.id = :custom_id", nativeQuery = true)
    Custom searchCustomById(@Param("custom_id") int customId);

    @Query(value = "SELECT * FROM custom AS c WHERE c.employee_id = :employee_id AND (c.status = 'IN_PROCESSING' OR c.status = 'WAITING_RESPONSE') ORDER BY c.creation_date ASC", nativeQuery = true)
    List<Custom> getProcessingCustomsForEmployee(@Param("employee_id") int employeeId);

    @Query(value = "SELECT * FROM custom AS c WHERE c.employee_id = :employee_id AND c.status = 'PROCESSED' ORDER BY c.creation_date DESC", nativeQuery = true)
    List<Custom> getProcessedCustomsForEmployee(@Param("employee_id") int employeeId);
}
