package com.example.WarehouseDatabaseJava.MyISAM.model.order;

import com.example.WarehouseDatabaseJava.InnoDB.model.order.Custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomMyIsamRepository extends JpaRepository <CustomMyISAM, Integer> {
    @Procedure("create_custom")
    @Modifying
    int createCustom(@Param("new_customer_id") int customerId, @Param("new_department_id") int departmentId);

    @Procedure("assign_employee_to_custom")
    @Modifying
    void assignEmployeeToCustom(@Param("new_employee_id") int employeeId, @Param("new_custom_id") int customId);

    @Procedure("set_custom_sent")
    @Modifying
    void setCustomSent(@Param("custom_id") int customId);

    @Query(value = "SELECT * FROM custom_myisam", nativeQuery = true)
    List<CustomMyISAM> getAllCustoms();

    @Query(value = "SELECT * FROM custom_myisam AS c WHERE c.customer_id = :customer_id", nativeQuery = true)
    List<CustomMyISAM> getCustomsForCustomer(@Param("customer_id") int customerId);

    @Query(value = "SELECT c.id, c.status, c.customer_id, c.department_id, c.employee_id, c.price, c.creation_date FROM custom_myisam AS c JOIN manager_department_myisam AS md ON c.department_id = md.department_id WHERE c.employee_id IS NULL AND md.manager_id = :manager_id AND (c.status = 'CREATED' OR c.status = 'IN_PROCESSING' OR c.status = 'PROCESSED') ORDER BY c.creation_date ASC", nativeQuery = true)
    List<CustomMyISAM> getAllCustomsWithoutAssignEmployee(@Param("manager_id") int managerId);

    @Query(value = "SELECT * FROM custom_myisam AS c WHERE c.id = :custom_id", nativeQuery = true)
    CustomMyISAM searchCustomById(@Param("custom_id") int customId);

    @Query(value = "SELECT * FROM custom_myisam AS c WHERE c.employee_id = :employee_id AND (c.status = 'IN_PROCESSING' OR c.status = 'WAITING_RESPONSE') ORDER BY c.creation_date ASC", nativeQuery = true)
    List<CustomMyISAM> getProcessingCustomsForEmployee(@Param("employee_id") int employeeId);

    @Query(value = "SELECT * FROM custom_myisam AS c WHERE c.employee_id = :employee_id AND c.status = 'PROCESSED' ORDER BY c.creation_date DESC", nativeQuery = true)
    List<CustomMyISAM> getProcessedCustomsForEmployee(@Param("employee_id") int employeeId);
}
