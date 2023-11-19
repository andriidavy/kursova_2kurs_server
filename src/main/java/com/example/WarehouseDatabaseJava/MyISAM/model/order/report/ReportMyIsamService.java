package com.example.WarehouseDatabaseJava.MyISAM.model.order.report;

import com.example.WarehouseDatabaseJava.dto.report.ReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMyIsamService.class);
    @Autowired
    ReportMyIsamRepository reportMyIsamRepository;

    public void provideReport(int employeeId, int customId, String reportText) {
        try {
            reportMyIsamRepository.provideReport(employeeId, customId, reportText);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void setReportAccepted(int reportId) {
        try {
            reportMyIsamRepository.setReportAccepted(reportId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void setReportRejected(int reportId) {
        try {
            reportMyIsamRepository.setReportRejected(reportId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ReportDTO> getAllWaitingReportsForManager(int managerId) {
        try {
            return convertReportToDTO(reportMyIsamRepository.getAllWaitingReportsForManager(managerId));
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ReportDTO> getAllWaitingReportsForEmployee(int employeeId) {
        try {
            return convertReportToDTO(reportMyIsamRepository.getAllWaitingReportsForEmployee(employeeId));
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ReportDTO> getAllAcceptedReportsForEmployee(int employeeId) {
        try {
            return convertReportToDTO(reportMyIsamRepository.getAllAcceptedReportsForEmployee(employeeId));
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ReportDTO> getAllRejectedReportsForEmployee(int employeeId) {
        try {
            return convertReportToDTO(reportMyIsamRepository.getAllRejectedReportsForEmployee(employeeId));
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ReportDTO> convertReportToDTO(List<ReportMyISAM> reports) {
        return reports.stream()
                .map(report -> new ReportDTO(report.getId(), report.getCustomId(), report.getReportText(), report.getStatus().toString()))
                .collect(Collectors.toList());
    }
}
