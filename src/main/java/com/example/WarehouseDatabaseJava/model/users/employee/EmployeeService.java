package com.example.WarehouseDatabaseJava.model.users.employee;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerProfileDTO;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.Cart;
import com.example.WarehouseDatabaseJava.model.users.manager.Manager;
import com.example.WarehouseDatabaseJava.model.users.manager.ManagerProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    //TESTED
    public Employee save(String name, String surname, String email, String password) {
        Employee existingEmployee = employeeRepository.findByEmail(email);
        if (existingEmployee != null) {
            throw new IllegalArgumentException("Employee with the same email already exists");
        }

        Employee employee = new Employee(name, surname, email, password);
        employeeRepository.save(employee);

        return employee;
    }

    public void deleteId(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee loginEmployee(String email, String password) {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getEmail().equals(email) && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        throw new RuntimeException("Invalid email or password");
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

    public EmployeeProfileDTO getEmployeeProfile(int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Manager not found with id: " + employeeId);
        }
        String name = employee.getName();
        String surname = employee.getSurname();
        String email = employee.getEmail();

        return new EmployeeProfileDTO(name, surname, email);
    }
}
