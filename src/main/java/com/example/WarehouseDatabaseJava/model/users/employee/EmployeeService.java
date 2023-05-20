package com.example.WarehouseDatabaseJava.model.users.employee;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    //TESTED
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteId(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeProfileDTO> getAllEmployeesProfile() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeProfileDTO> employeeProfiles = new ArrayList<>();

        for (Employee employee : employees) {
            int id = employee.getId();
            String name = employee.getName();
            String surname = employee.getSurname();
            String email = employee.getEmail();

            EmployeeProfileDTO profile = new EmployeeProfileDTO(id, name, surname, email);
            employeeProfiles.add(profile);
        }

        return employeeProfiles;
    }
}
