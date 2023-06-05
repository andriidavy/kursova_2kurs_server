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

    // метод створення звіту робітником для конкретного замовлення TESTED
    //ОБНОВА!!!
    @Transactional
    public void createReport(int employeeId, int customId, String reportText) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        Custom custom = customRepository.getReferenceById(customId);

        if (employee != null && custom != null) {
            // Check if the custom belongs to the employee
            if (!employee.getCustomList().contains(custom)) {
                throw new IllegalArgumentException("Custom does not belong to the employee");
            }
            if (custom.getStatus() != Custom.Status.IN_PROCESSING) {
                throw new IllegalArgumentException("Report can only be created for Custom with Status IN_PROCESSING");
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
                    throw new IllegalArgumentException("Report for this Custom already exists and cannot be updated or created");
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
    }

    // метод для отримання всіх звітів, у яких статус WAITING (очікують відповіді менеджера) TESTED
    //ОБНОВА!!!

    public List<ReportDTO> getAllWaitingReportsForManager(int managerId) {
        Manager manager = managerRepository.getReferenceById(managerId);
        List<Department> managerDepartments = manager.getDepartmentList();

        List<Report> allReports = reportRepository.findAllByStatus(Report.Status.WAITING);
        List<ReportDTO> reportsDTO = new ArrayList<>();

        for (Report report : allReports) {
            Custom custom = report.getCustom();
            Department department = custom.getDepartment();

            if (managerDepartments.contains(department)) {
                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setReportId(report.getId());
                reportDTO.setCustomId(custom.getId());
                reportDTO.setReportText(report.getReportText());
                reportDTO.setStatus(report.getStatus().toString());

                reportsDTO.add(reportDTO);
            }
        }

        return reportsDTO;
    }

    //метод отримання всіх звітів зі статусом WAITING для конкретного робітника TESTED
    //ОБНОВА!!!

    public List<ReportDTO> getAllWaitingReportsForEmployee(int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);

        List<ReportDTO> reportsDTO = new ArrayList<>();

        for (Custom custom : employee.getCustomList()) {
            Report report = custom.getReport();
            if (report != null && report.getStatus() == Report.Status.WAITING) {
                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setReportId(report.getId());
                reportDTO.setCustomId(custom.getId());
                reportDTO.setReportText(report.getReportText());
                reportDTO.setStatus(report.getStatus().toString());

                reportsDTO.add(reportDTO);
            }
        }
        return reportsDTO;
    }

    //метод отримання всіх звітів зі статусом ACCEPTED для конкретного робітника TESTED
    //ОБНОВА!!!

    public List<ReportDTO> getAllAcceptedReportsForEmployee(int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);

        List<ReportDTO> reportsDTO = new ArrayList<>();

        for (Custom custom : employee.getCustomList()) {
            Report report = custom.getReport();
            if (report != null && report.getStatus() == Report.Status.ACCEPTED) {
                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setReportId(report.getId());
                reportDTO.setCustomId(custom.getId());
                reportDTO.setReportText(report.getReportText());
                reportDTO.setStatus(report.getStatus().toString());

                reportsDTO.add(reportDTO);
            }
        }
        return reportsDTO;
    }

    //метод отримання всіх звітів зі статусом REJECTED для конкретного робітника TESTED
    //ОБНОВА!!!

    public List<ReportDTO> getAllRejectedReportsForEmployee(int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);

        List<ReportDTO> reportsDTO = new ArrayList<>();

        for (Custom custom : employee.getCustomList()) {
            Report report = custom.getReport();
            if (report != null && report.getStatus() == Report.Status.REJECTED) {
                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setReportId(report.getId());
                reportDTO.setCustomId(custom.getId());
                reportDTO.setReportText(report.getReportText());
                reportDTO.setStatus(report.getStatus().toString());

                reportsDTO.add(reportDTO);
            }
        }
        return reportsDTO;
    }

    // метод для встановлення конкретному звіту статусу ACCEPTED TESTED
    @Transactional
    public void setReportAccepted(int reportId) {
        Report report = reportRepository.getReferenceById(reportId);
        if (report == null) {
            throw new EntityNotFoundException("Report with id " + reportId + " not found");
        }
        if (report.getStatus() != Report.Status.WAITING) {
            throw new IllegalStateException("Report with id " + reportId + " cannot be accepted because it is not in WAITING status");
        }
        report.setStatus(Report.Status.ACCEPTED);
        reportRepository.save(report);
        customService.setCustomProcessed(report.getCustom().getId());
    }

    // метод для встановлення конкретному звіту статусу REJECTED TESTED
    @Transactional
    public void setReportRejected(int reportId) {
        Report report = reportRepository.getReferenceById(reportId);
        if (report != null) {
            report.setStatus(Report.Status.REJECTED);
            reportRepository.save(report);
            customService.setCustomInProcessing(report.getCustom().getId());
        }
    }

}
