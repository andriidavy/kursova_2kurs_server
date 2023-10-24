package com.example.WarehouseDatabaseJava.MyISAM.model.order.report;

import com.example.WarehouseDatabaseJava.InnoDB.model.order.report.Report;
import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_myisam", name = "report_myisam")
public class ReportMyISAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reportText;

    public enum Status{
        WAITING(1),
        ACCEPTED(2),
        REJECTED(3);
        private final int value;
        Status(int value){
            this.value=value;
        }

        public int getValue() {
            return value;
        }
    }

    //enumerated вказує на те як буде відображатися значення статусу, в данному випадку це буде рядкове значення
    @Enumerated(EnumType.STRING)
    private Report.Status status;
    private int customId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public Report.Status getStatus() {
        return status;
    }

    public void setStatus(Report.Status status) {
        this.status = status;
    }

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }
}
