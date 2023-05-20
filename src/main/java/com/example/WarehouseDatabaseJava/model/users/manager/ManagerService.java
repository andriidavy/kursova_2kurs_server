package com.example.WarehouseDatabaseJava.model.users.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;

    public Manager save (Manager manager){
        return managerRepository.save(manager);
    }
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }
}
