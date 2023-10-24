package com.example.WarehouseDatabaseJava.InnoDB.model.users.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Boolean existsByEmail(String email);
    Boolean existsByEmailAndPassword(String email, String password);
    Manager getReferenceByEmailAndPassword(String email, String password);
}
