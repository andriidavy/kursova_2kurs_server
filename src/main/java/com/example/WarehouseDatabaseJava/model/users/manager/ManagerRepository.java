package com.example.WarehouseDatabaseJava.model.users.manager;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends CrudRepository<Manager,Integer> {
}
