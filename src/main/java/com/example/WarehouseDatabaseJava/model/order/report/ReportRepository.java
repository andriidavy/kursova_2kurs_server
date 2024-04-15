package com.example.WarehouseDatabaseJava.model.order.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Procedure("_provide_report")
    @Modifying
    void provideReport(@Param("new_employee_id") int employeeId, @Param("new_custom_id") int customId, @Param("new_report_text") String reportText);

    @Procedure("_set_report_accepted")
    @Modifying
    void setReportAccepted(@Param("new_report_id") int reportId);

    @Procedure("_set_report_rejected")
    @Modifying
    void setReportRejected(@Param("new_report_id") int reportId);

    @Query(value = "SELECT r.id, r.custom_id, r.report_text, r.status, r.update_date FROM report AS r JOIN custom AS c ON r.custom_id = c.id JOIN manager_department AS md ON c.department_id = md.department_id WHERE md.manager_id = :manager_id AND r.status = 'WAITING'", nativeQuery = true)
    List<Report> getAllWaitingReportsForManager(@Param("manager_id") int managerId);

    @Query(value = "SELECT r.id, r.custom_id, r.report_text, r.status, r.update_date FROM report AS r JOIN custom AS c ON r.custom_id = c.id WHERE c.employee_id = :employee_id AND r.status = 'WAITING'", nativeQuery = true)
    List<Report> getAllWaitingReportsForEmployee(@Param("employee_id") int employeeId);

    @Query(value = "SELECT r.id, r.custom_id, r.report_text, r.status, r.update_date FROM report AS r JOIN custom AS c ON r.custom_id = c.id WHERE c.employee_id = :employee_id AND r.status = 'ACCEPTED'", nativeQuery = true)
    List<Report> getAllAcceptedReportsForEmployee(@Param("employee_id") int employeeId);

    @Query(value = "SELECT r.id, r.custom_id, r.report_text, r.status, r.update_date FROM report AS r JOIN custom AS c ON r.custom_id = c.id WHERE c.employee_id = :employee_id AND r.status = 'REJECTED'", nativeQuery = true)
    List<Report> getAllRejectedReportsForEmployee(@Param("employee_id") int employeeId);
}
