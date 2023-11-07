package com.example.WarehouseDatabaseJava.MyISAM.model.order.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportMyIsamRepository extends JpaRepository <ReportMyISAM, Integer> {

    @Procedure("provide_report")
    @Modifying
    void provideReport(@Param("new_custom_id") int customId, @Param("new_report_text") String reportText);

    @Procedure("set_report_accepted")
    @Modifying
    void setReportAccepted(@Param("new_report_id") int reportId);

    @Procedure("set_report_rejected")
    @Modifying
    void setReportRejected(@Param("new_report_id") int reportId);
}
