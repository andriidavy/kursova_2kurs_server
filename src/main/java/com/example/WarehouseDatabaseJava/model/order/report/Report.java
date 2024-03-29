package com.example.WarehouseDatabaseJava.model.order.report;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import jakarta.persistence.*;

@Entity
public class Report {
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

    //One-to-One relation with Custom
    @OneToOne
    @JoinColumn(name = "custom_id")
    Custom custom;

    public Report.Status getStatus() {
        return status;
    }

    public void setStatus(Report.Status status) {
        this.status = status;
    }

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

    public Custom getCustom() {
        return custom;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }
}
