package com.example.WarehouseDatabaseJava.dto.report;

import com.example.WarehouseDatabaseJava.MyISAM.model.order.report.ReportMyISAM;

public class ReportDTO {
    private int reportId;
    private int customId;
    private String reportText;
    private String status;

    public ReportDTO() {
    }

    public ReportDTO(int reportId, int customId, String reportText, String status) {
        this.reportId = reportId;
        this.customId = customId;
        this.reportText = reportText;
        this.status = status;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
