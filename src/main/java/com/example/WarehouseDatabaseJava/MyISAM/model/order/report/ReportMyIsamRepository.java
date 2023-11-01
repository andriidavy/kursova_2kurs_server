package com.example.WarehouseDatabaseJava.MyISAM.model.order.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportMyIsamRepository extends JpaRepository <ReportMyISAM, Integer> {
}
