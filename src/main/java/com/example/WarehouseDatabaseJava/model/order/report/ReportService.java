package com.example.WarehouseDatabaseJava.model.order.report;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import com.example.WarehouseDatabaseJava.model.order.CustomRepository;
import com.example.WarehouseDatabaseJava.model.order.CustomService;
import com.example.WarehouseDatabaseJava.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeCustom;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CustomRepository customRepository;
    @Autowired
    CustomService customService;

    // метод створення звіту робітником для конкретного замовлення
    public void createReport(int employeeId, int customId, String reportText) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        Custom custom = customRepository.getReferenceById(customId);

        if (employee != null && custom != null) {
            // Check if the custom belongs to the employee
            boolean hasCustom = false;
            for (EmployeeCustom ec : employee.getEmployeeCustomList()) {
                if (ec.getCustom().getId() == custom.getId()) {
                    hasCustom = true;
                    break;
                }
            }
            if (!hasCustom) {
                throw new IllegalArgumentException("Custom does not belong to the employee");
            }

            Report report = new Report();
            report.setReportText(reportText);
            report.setStatus(Report.Status.WAITING);
            report.setCustom(custom);

            reportRepository.save(report);
        }
    }

//    // метод отримання всіх звітів
//    public List<Report> getAllReports() {
//        return reportRepository.findAll();
//    }

    // метод для отримання всіх звітів, у яких статус WAITING (очікують відповіді менеджера)
    public List<Report> getAllWaitingReports() {
        List<Report> allWaitingReports = reportRepository.findAll();
        return allWaitingReports.stream()
                .filter(report -> report.getStatus() == Report.Status.WAITING)
                .collect(Collectors.toList());
    }

    //метод отримання всіх звітів зі статусом WAITING та REJECTED для конкретного робітника
    public List<Report> getAllWaitingOrRejectedReportsForEmployee(int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);

        List<Custom> customs = employee.getEmployeeCustomList().stream()
                .map(EmployeeCustom::getCustom)
                .toList();

        List<Report> reports = new ArrayList<>();
        for (Custom custom : customs) {
            Report report = custom.getReport();
            if (report != null && (report.getStatus() == Report.Status.WAITING || report.getStatus() == Report.Status.REJECTED)) {
                reports.add(report);
            }
        }
        return reports;
    }

    // метод для встановлення конкретному звіту статусу ACCEPTED
    @Transactional
    public void setReportAccepted(int reportId) {
        Report report = reportRepository.getReferenceById(reportId);
        if (report == null) {
            throw new EntityNotFoundException("Report with id " + reportId + " not found");
        }
        if (report.getStatus() != Report.Status.WAITING || report.getStatus()!= Report.Status.REJECTED) {
            throw new IllegalStateException("Report with id " + reportId + " cannot be accepted because it is not in WAITING/REJECTED status");
        }
        report.setStatus(Report.Status.ACCEPTED);
        reportRepository.save(report);
        customService.setCustomProcessed(report.getCustom().getId());
    }

    // метод для встановлення конкретному звіту статусу REJECTED
    public void setReportRejected(int reportId) {
        Report report = reportRepository.getReferenceById(reportId);
        if (report != null) {
            report.setStatus(Report.Status.REJECTED);
            reportRepository.save(report);
        }
    }

}
