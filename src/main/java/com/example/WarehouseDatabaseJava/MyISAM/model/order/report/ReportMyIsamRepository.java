package com.example.WarehouseDatabaseJava.MyISAM.model.order.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportMyIsamRepository extends JpaRepository<ReportMyISAM, Integer> {
    @Procedure("provide_report")
    @Modifying
    void provideReport(@Param("new_employee_id") int employeeId, @Param("new_custom_id") int customId, @Param("new_report_text") String reportText);

    @Procedure("set_report_accepted")
    @Modifying
    void setReportAccepted(@Param("new_report_id") int reportId);

    @Procedure("set_report_rejected")
    @Modifying
    void setReportRejected(@Param("new_report_id") int reportId);

    @Query(value = "SELECT r.id, r.custom_id, r.report_text, r.status, r.update_date FROM report_myisam AS r JOIN custom_myisam AS c ON r.custom_id = c.id JOIN manager_department_myisam AS md ON c.department_id = md.department_id WHERE md.manager_id = :manager_id AND r.status = 'WAITING' ORDER BY r.update_date ASC", nativeQuery = true)
    List<ReportMyISAM> getAllWaitingReportsForManager(@Param("manager_id") int managerId);

    @Query(value = "SELECT r.id, r.custom_id, r.report_text, r.status, r.update_date FROM report_myisam AS r JOIN custom_myisam AS c ON r.custom_id = c.id WHERE c.employee_id = :employee_id AND r.status = 'WAITING' ORDER BY r.update_date ASC", nativeQuery = true)
    List<ReportMyISAM> getAllWaitingReportsForEmployee(@Param("employee_id") int employeeId);

    @Query(value = "SELECT r.id, r.custom_id, r.report_text, r.status, r.update_date FROM report_myisam AS r JOIN custom_myisam AS c ON r.custom_id = c.id WHERE c.employee_id = :employee_id AND r.status = 'ACCEPTED' ORDER BY r.update_date ASC", nativeQuery = true)
    List<ReportMyISAM> getAllAcceptedReportsForEmployee(@Param("employee_id") int employeeId);

    @Query(value = "SELECT r.id, r.custom_id, r.report_text, r.status, r.update_date FROM report_myisam AS r JOIN custom_myisam AS c ON r.custom_id = c.id WHERE c.employee_id = :employee_id AND r.status = 'REJECTED' ORDER BY r.update_date ASC", nativeQuery = true)
    List<ReportMyISAM> getAllRejectedReportsForEmployee(@Param("employee_id") int employeeId);
}
