package com.example.WarehouseDatabaseJava.dto.report;

public class ReportDTO {
    private int reportId;
    private int customId;
    private String reportText;
    private String status;
    private String updateDate;

    public ReportDTO() {
    }

    public ReportDTO(int reportId, int customId, String reportText, String status, String updateDate) {
        this.reportId = reportId;
        this.customId = customId;
        this.reportText = reportText;
        this.status = status;
        this.updateDate = updateDate;
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

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
