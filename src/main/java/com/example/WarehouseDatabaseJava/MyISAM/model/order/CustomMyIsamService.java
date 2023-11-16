package com.example.WarehouseDatabaseJava.MyISAM.model.order;

import com.example.WarehouseDatabaseJava.MyISAM.model.department.DepartmentMyIsamRepository;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.CustomerMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.CustomerMyIsamRepository;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyIsamRepository;
import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomMyIsamService {
    private static final Logger logger = LoggerFactory.getLogger(CustomMyIsamService.class);
    @Autowired
    CustomMyIsamRepository customMyIsamRepository;
    @Autowired
    CustomProductMyIsamRepository customProductMyIsamRepository;
    @Autowired
    CustomerMyIsamRepository customerMyIsamRepository;
    @Autowired
    EmployeeMyIsamRepository employeeMyIsamRepository;
    @Autowired
    DepartmentMyIsamRepository departmentMyIsamRepository;

    //for customer
    public List<CustomDTO> getCustomsForCustomer(int customerId) {
        try {
            List<CustomMyISAM> customs = customMyIsamRepository.getCustomsForCustomer(customerId);
            return convertCustomToDTO(customs, false);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    //for manager
    public List<CustomDTO> getAllCustoms() {
        try {
            List<CustomMyISAM> allCustoms = customMyIsamRepository.getAllCustoms();
            return convertCustomToDTO(allCustoms, true);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<CustomDTO> getAllCustomsWithoutAssignEmployee(int managerId) {
        try {
            List<CustomMyISAM> customs = customMyIsamRepository.getAllCustomsWithoutAssignEmployee(managerId);
            return convertCustomToDTO(customs, true);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    //for employee
    public List<CustomDTO> getProcessingCustomsForEmployee(int employeeId) {
        try {
            List<CustomMyISAM> customs = customMyIsamRepository.getProcessingCustomsForEmployee(employeeId);
            return convertCustomToDTO(customs, false);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<CustomDTO> getProcessedCustomsForEmployee(int employeeId) {
        try {
            List<CustomMyISAM> customs = customMyIsamRepository.getProcessedCustomsForEmployee(employeeId);
            return convertCustomToDTO(customs, false);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public int createCustom(int customerId, int departmentId) {
        try {
            return customMyIsamRepository.createCustom(customerId, departmentId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(value = "db2TransactionManager")
    public void assignEmployeeToCustom(int employeeId, int customId) {
        try {
            customMyIsamRepository.assignEmployeeToCustom(employeeId, customId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void setCustomSent(int customId) {
        try {
            customMyIsamRepository.setCustomSent(customId);
        } catch (DataAccessException e) {
            logger.error("An exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    private List<CustomDTO> convertCustomToDTO(List<CustomMyISAM> customs, boolean workerSetup) {
        List<CustomDTO> customDTOList = new ArrayList<>();

        for (CustomMyISAM custom : customs) {
            CustomDTO customDTO = new CustomDTO();

            customDTO.setCustomId(custom.getId());

            CustomerMyISAM customer = customerMyIsamRepository.getCustomerById(custom.getCustomerId());
            customDTO.setCustomerId(customer.getId());
            customDTO.setCustomerName(customer.getName());
            customDTO.setCustomerSurname(customer.getSurname());

            if (custom.getEmployeeId() != null && workerSetup) {
                EmployeeMyISAM employee = employeeMyIsamRepository.getEmployeeById(custom.getEmployeeId());
                customDTO.setEmployeeId(employee.getId());
                customDTO.setEmployeeName(employee.getName());
                customDTO.setEmployeeSurname(employee.getSurname());
            }

            customDTO.setStatus(custom.getStatus().toString());
            customDTO.setDepartment(departmentMyIsamRepository.getDepartmentNameById(custom.getDepartmentId()));
            customDTO.setCustomProductDTOList(customProductMyIsamRepository.getCustomProductDTOListByCustomId(custom.getId()));

            customDTOList.add(customDTO);
        }
        return customDTOList;
    }
}
