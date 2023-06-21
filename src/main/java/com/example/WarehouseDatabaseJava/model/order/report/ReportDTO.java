package com.example.WarehouseDatabaseJava.model.order.report;

public class ReportDTO {
    private String reportId;
    private String customId;
    private String reportText;
    private String callbackText;
    private String status;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public String getCallbackText() {
        return callbackText;
    }

    public void setCallbackText(String callbackText) {
        this.callbackText = callbackText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
