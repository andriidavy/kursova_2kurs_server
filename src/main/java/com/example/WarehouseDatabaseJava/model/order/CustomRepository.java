package com.example.WarehouseDatabaseJava.model.order;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRepository extends JpaRepository<Custom, Integer> {
    List<Custom> findAllByStatus(Custom.Status status);
}
