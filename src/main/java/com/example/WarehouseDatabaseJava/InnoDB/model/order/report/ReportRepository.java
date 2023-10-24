package com.example.WarehouseDatabaseJava.InnoDB.model.order.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findAllByStatus(Report.Status status);
}
