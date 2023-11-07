package com.example.WarehouseDatabaseJava.MyISAM.model.order.report;

import com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.CustomerMyIsamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ReportMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerMyIsamService.class);
    @Autowired
    ReportMyIsamRepository reportMyIsamRepository;

    public void provideReport(int customId, String reportText) {
        try {
            reportMyIsamRepository.provideReport(customId, reportText);
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

}
