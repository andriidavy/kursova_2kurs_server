package com.example.WarehouseDatabaseJava.model.order.report;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.order.CustomRepository;
import com.example.WarehouseDatabaseJava.model.order.CustomService;
import com.example.WarehouseDatabaseJava.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeRepository;
import com.example.WarehouseDatabaseJava.model.users.manager.Manager;
import com.example.WarehouseDatabaseJava.model.users.manager.ManagerRepository;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.Department;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    CustomRepository customRepository;
    @Autowired
    CustomService customService;

    // метод створення звіту робітником для конкретного замовлення (TESTED!)
    //ОБНОВА!!!
    @Transactional
    public void createReport(String employeeId, String customId, String reportText) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id:" + employeeId);
        }
        if (!customRepository.existsById(customId)) {
            throw new EntityNotFoundException("Custom not found with id:" + customId);
        }
        if (reportText == null) {
            throw new NullPointerException("reportText is null");
        }

        Employee employee = employeeRepository.getReferenceById(employeeId);
        Custom custom = customRepository.getReferenceById(customId);

        // Check if the custom belongs to the employee
        if (!employee.getCustomList().contains(custom)) {
            throw new RuntimeException("Custom does not belong to the employee");
        }
        if (custom.getStatus() != Custom.Status.IN_PROCESSING) {
            throw new IllegalStateException("Report can only be created for Custom with Status IN_PROCESSING");
        }

        Report existingReport = custom.getReport();
        if (existingReport != null) {
            if (existingReport.getStatus() == Report.Status.REJECTED) {
                existingReport.setReportText(reportText);
                existingReport.setStatus(Report.Status.WAITING);
                custom.setStatus(Custom.Status.WAITING_RESPONSE);
                reportRepository.save(existingReport);
                customRepository.save(custom);
            } else {
                throw new IllegalStateException("Report for Custom: " + customId + " already exists and cannot be updated or created");
            }
        } else {
            Report report = new Report();
            report.setReportText(reportText);
            report.setStatus(Report.Status.WAITING);
            custom.setStatus(Custom.Status.WAITING_RESPONSE);
            report.setCustom(custom);

            reportRepository.save(report);
            customRepository.save(custom);
        }

    }

    // метод для отримання всіх звітів, у яких статус WAITING (очікують відповіді менеджера) TESTED
    //ОБНОВА!!!

    public List<ReportDTO> getAllWaitingReportsForManager(String managerId) {
        if (!managerRepository.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id:" + managerId);
        }

        Manager manager = managerRepository.getReferenceById(managerId);
        List<Department> managerDepartments = manager.getDepartmentList();

        List<Report> allReports = reportRepository.findAllByStatus(Report.Status.WAITING);
        List<ReportDTO> reportsDTO = new ArrayList<>();

        for (Report report : allReports) {
            Custom custom = report.getCustom();
            Department department = custom.getDepartment();

            if (managerDepartments.contains(department)) {
                ReportDTO reportDTO = setReportDTO(report, custom);

                reportsDTO.add(reportDTO);
            }
        }
        return reportsDTO;
    }

    //метод отримання всіх звітів зі статусом WAITING для конкретного робітника TESTED
    //ОБНОВА!!!

    public List<ReportDTO> getAllWaitingReportsForEmployee(String employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id:" + employeeId);
        }

        Employee employee = employeeRepository.getReferenceById(employeeId);

        List<ReportDTO> reportsDTO = new ArrayList<>();

        for (Custom custom : employee.getCustomList()) {
            Report report = custom.getReport();
            if (report != null && report.getStatus() == Report.Status.WAITING) {
                ReportDTO reportDTO = setReportDTO(report, custom);

                reportsDTO.add(reportDTO);
            }
        }
        return reportsDTO;
    }

    //метод отримання всіх звітів зі статусом ACCEPTED для конкретного робітника TESTED
    //ОБНОВА!!!

    public List<ReportDTO> getAllAcceptedReportsForEmployee(String employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id:" + employeeId);
        }

        Employee employee = employeeRepository.getReferenceById(employeeId);

        List<ReportDTO> reportsDTO = new ArrayList<>();

        for (Custom custom : employee.getCustomList()) {
            Report report = custom.getReport();
            if (report != null && report.getStatus() == Report.Status.ACCEPTED) {
                ReportDTO reportDTO = setReportDTO(report, custom);

                reportsDTO.add(reportDTO);
            }
        }
        return reportsDTO;
    }

    //метод отримання всіх звітів зі статусом REJECTED для конкретного робітника TESTED
    //ОБНОВА!!!

    public List<ReportDTO> getAllRejectedReportsForEmployee(String employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id:" + employeeId);
        }

        Employee employee = employeeRepository.getReferenceById(employeeId);

        List<ReportDTO> reportsDTO = new ArrayList<>();

        for (Custom custom : employee.getCustomList()) {
            Report report = custom.getReport();
            if (report != null && report.getStatus() == Report.Status.REJECTED) {
                ReportDTO reportDTO = setReportDTO(report, custom);
                reportDTO.setCallbackText(report.getCallbackText());

                reportsDTO.add(reportDTO);
            }
        }
        return reportsDTO;
    }

    // метод для встановлення конкретному звіту статусу ACCEPTED TESTED
    @Transactional
    public void setReportAccepted(String reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new EntityNotFoundException("Report not found with id:" + reportId);
        }

        Report report = reportRepository.getReferenceById(reportId);

        if (report.getStatus() != Report.Status.WAITING) {
            throw new IllegalStateException("Report with id " + reportId + " cannot be accepted because it is not in WAITING status");
        }
        report.setStatus(Report.Status.ACCEPTED);
        if (report.getCallbackText() != null) {
            report.setCallbackText(null);
        }
        reportRepository.save(report);
        customService.setCustomProcessed(report.getCustom().getId());
    }

    // метод для встановлення конкретному звіту статусу REJECTED (TESTED!)
    @Transactional
    public void setReportRejected(String reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new EntityNotFoundException("Report not found with id:" + reportId);
        }

        Report report = reportRepository.getReferenceById(reportId);

        report.setStatus(Report.Status.REJECTED);
        reportRepository.save(report);
        customService.setCustomInProcessing(report.getCustom().getId());
    }

    //метод для встановлення зворотнього повідомлення callback для відхиленого звіту (TESTED!)
    public void setReportCallback(String reportId, String callbackText) {
        if (!reportRepository.existsById(reportId)) {
            throw new EntityNotFoundException("Report not found with id:" + reportId);
        }

        Report report = reportRepository.getReferenceById(reportId);

        if (report.getStatus() != Report.Status.REJECTED) {
            throw new IllegalStateException("Report with id " + reportId + " cannot have callback because it is not in REJECTED status");
        }

        report.setCallbackText(callbackText);
        reportRepository.save(report);
    }

    //внутрішній метод встановлення інфи про звіт для замовлення
    private ReportDTO setReportDTO(Report report, Custom custom) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportId(report.getId());
        reportDTO.setCustomId(custom.getId());
        reportDTO.setReportText(report.getReportText());
        reportDTO.setStatus(report.getStatus().toString());
        return reportDTO;
    }
}
