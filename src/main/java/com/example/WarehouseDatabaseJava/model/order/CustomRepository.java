package com.example.WarehouseDatabaseJava.model.order;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomRepository extends JpaRepository<Custom, String> {
    List<Custom> findAllByStatus(Custom.Status status);

    List<Custom> findAllByCreationTime(Date creatingTime);
}
