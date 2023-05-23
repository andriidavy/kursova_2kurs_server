package com.example.WarehouseDatabaseJava.model.users.manager;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerProfileDTO;
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

    public ManagerProfileDTO getManagerProfile(int managerId) {
        Manager manager = managerRepository.getReferenceById(managerId);
        if (manager == null) {
            throw new RuntimeException("Manager not found with id: " + managerId);
        }
        String name = manager.getName();
        String surname = manager.getSurname();
        String email = manager.getEmail();

        return new ManagerProfileDTO(name, surname, email);
    }
}
