package com.example.WarehouseDatabaseJava.MyISAM.model.order.report;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(catalog = "warehouse_database_myisam", name = "report_myisam")
public class ReportMyISAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "report_text")
    private String reportText;

    @Column(name = "update_date")
    private LocalDate updateDate;

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
    @Column(name = "status")
    private ReportMyISAM.Status status;
    @Column(name = "custom_id")
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

    public ReportMyISAM.Status getStatus() {
        return status;
    }

    public void setStatus(ReportMyISAM.Status status) {
        this.status = status;
    }

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }
}
