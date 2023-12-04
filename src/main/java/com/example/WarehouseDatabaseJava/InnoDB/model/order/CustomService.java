package com.example.WarehouseDatabaseJava.InnoDB.model.order;

import com.example.WarehouseDatabaseJava.InnoDB.model.department.DepartmentRepository;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.CustomerRepository;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.employee.EmployeeRepository;
import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
import com.example.WarehouseDatabaseJava.dto.custom.CustomProductDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomService {
    private static final Logger logger = LoggerFactory.getLogger(CustomService.class);
    @Autowired
    CustomRepository customRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    //for customer
    public List<CustomDTO> getCustomsForCustomer(int customerId) {
        try {
            List<Custom> customs = customRepository.getCustomsForCustomer(customerId);
            return convertCustomToDTO(customs, false);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    //for manager
    public List<CustomDTO> getAllCustoms() {
        try {
            List<Custom> allCustoms = customRepository.getAllCustoms();
            return convertCustomToDTO(allCustoms, true);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<CustomDTO> getAllCustomsWithoutAssignEmployee(int managerId) {
        try {
            List<Custom> customs = customRepository.getAllCustomsWithoutAssignEmployee(managerId);
            return convertCustomToDTO(customs, true);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public CustomDTO searchCustomById(int customId) {
        try {
            Custom custom = customRepository.searchCustomById(customId);
            CustomDTO customDTO = new CustomDTO();
            customDTO.setCustomId(custom.getId());

            Customer customer = customerRepository.getCustomerById(custom.getCustomer().getId());
            customDTO.setCustomerId(customer.getId());
            customDTO.setCustomerName(customer.getName());
            customDTO.setCustomerSurname(customer.getSurname());

            if (custom.getEmployee() != null) {
                Employee employee = employeeRepository.getEmployeeById(custom.getEmployee().getId());
                customDTO.setEmployeeId(employee.getId());
                customDTO.setEmployeeName(employee.getName());
                customDTO.setEmployeeSurname(employee.getSurname());
            }

            customDTO.setStatus(custom.getStatus().toString());
            customDTO.setPrice(custom.getPrice());
            customDTO.setDepartment(departmentRepository.getDepartmentNameById(custom.getDepartment().getId()));
            customDTO.setCustomProductDTOList(getCustomProductDTO(custom.getId()));
            return customDTO;
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    //for employee
    public List<CustomDTO> getProcessingCustomsForEmployee(int employeeId) {
        try {
            List<Custom> customs = customRepository.getProcessingCustomsForEmployee(employeeId);
            return convertCustomToDTO(customs, false);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<CustomDTO> getProcessedCustomsForEmployee(int employeeId) {
        try {
            List<Custom> customs = customRepository.getProcessedCustomsForEmployee(employeeId);
            return convertCustomToDTO(customs, false);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public int createCustom(int customerId, int departmentId) {
        try {
            return customRepository.createCustom(customerId, departmentId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void assignEmployeeToCustom(int customId, int employeeId) {
        try {
            customRepository.assignEmployeeToCustom(customId, employeeId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void setCustomSent(int customId) {
        try {
            customRepository.setCustomSent(customId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    private List<CustomDTO> convertCustomToDTO(List<Custom> customs, boolean workerSetup) {
        List<CustomDTO> customDTOList = new ArrayList<>();

        for (Custom custom : customs) {
            CustomDTO customDTO = new CustomDTO();

            customDTO.setCustomId(custom.getId());

            Customer customer = customerRepository.getCustomerById(custom.getCustomer().getId());
            customDTO.setCustomerId(customer.getId());
            customDTO.setCustomerName(customer.getName());
            customDTO.setCustomerSurname(customer.getSurname());

            if (custom.getEmployee() != null && workerSetup) {
                Employee employee = employeeRepository.getEmployeeById(custom.getEmployee().getId());
                customDTO.setEmployeeId(employee.getId());
                customDTO.setEmployeeName(employee.getName());
                customDTO.setEmployeeSurname(employee.getSurname());
            }

            customDTO.setStatus(custom.getStatus().toString());
            customDTO.setPrice(custom.getPrice());
            customDTO.setDepartment(departmentRepository.getDepartmentNameById(custom.getDepartment().getId()));
            customDTO.setCustomProductDTOList(getCustomProductDTO(custom.getId()));

            customDTOList.add(customDTO);
        }
        return customDTOList;
    }

    @Transactional(readOnly = true)
    public List<CustomProductDTO> getCustomProductDTO(int customId) {
        try {
            String nativeQuery = "SELECT cp.product_id, p.name, cp.quantity, cp.price FROM custom_product cp JOIN product p ON cp.product_id = p.id WHERE cp.custom_id = :custom_id";

            List<Object[]> resultList = entityManager.createNativeQuery(nativeQuery).setParameter("custom_id", customId).getResultList();

            return resultList.stream().map(result -> new CustomProductDTO(
                            (int) result[0],
                            (String) result[1],
                            (int) result[2],
                            ((BigDecimal) result[3]).doubleValue()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
