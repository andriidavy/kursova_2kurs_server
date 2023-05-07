package com.example.WarehouseDatabaseJava.model.users.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save (Employee employee){
        return employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Streamable.of(employeeRepository.findAll()).forEach(employees::add);
        return employees;
    }
}
