package com.example.WarehouseDatabaseJava.InnoDB.model.order.report;

import com.example.WarehouseDatabaseJava.dto.report.ReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    @Autowired
    ReportRepository reportRepository;

    public void provideReport(int employeeId, int customId, String reportText) {
        try {
            reportRepository.provideReport(employeeId, customId, reportText);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void setReportAccepted(int reportId) {
        try {
            reportRepository.setReportAccepted(reportId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void setReportRejected(int reportId) {
        try {
            reportRepository.setReportRejected(reportId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ReportDTO> getAllWaitingReportsForManager(int managerId) {
        try {
            return convertReportToDTO(reportRepository.getAllWaitingReportsForManager(managerId));
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ReportDTO> getAllWaitingReportsForEmployee(int employeeId) {
        try {
            return convertReportToDTO(reportRepository.getAllWaitingReportsForEmployee(employeeId));
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ReportDTO> getAllAcceptedReportsForEmployee(int employeeId) {
        try {
            return convertReportToDTO(reportRepository.getAllAcceptedReportsForEmployee(employeeId));
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ReportDTO> getAllRejectedReportsForEmployee(int employeeId) {
        try {
            return convertReportToDTO(reportRepository.getAllRejectedReportsForEmployee(employeeId));
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ReportDTO> convertReportToDTO(List<Report> reports) {
        return reports.stream()
                .map(report -> new ReportDTO(report.getId(), report.getCustom().getId(), report.getReportText(), report.getStatus().toString(), report.getUpdateDate().toString()))
                .collect(Collectors.toList());
    }
}
