package com.example.WarehouseDatabaseJava.MyISAM.model.order;

import com.example.WarehouseDatabaseJava.dto.custom.CustomDTO;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.CustomerMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.CustomerMyIsamRepository;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyISAM;
import com.example.WarehouseDatabaseJava.MyISAM.model.users.employee.EmployeeMyIsamRepository;
import com.example.WarehouseDatabaseJava.MyISAM.model.department.DepartmentMyIsamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomMyIsamService {
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

    public List<CustomDTO> getAllCustoms() {
        List<CustomMyISAM> allCustoms = customMyIsamRepository.getAllCustoms();
        List<CustomDTO> allCustomsDTOs = new ArrayList<>();

        for (CustomMyISAM custom : allCustoms) {
            CustomDTO customDTO = new CustomDTO();

            customDTO.setCustomId(custom.getId());

            CustomerMyISAM customer = customerMyIsamRepository.getCustomerById(custom.getCustomerId());
            customDTO.setCustomerId(customer.getId());
            customDTO.setCustomerName(customer.getName());
            customDTO.setCustomerSurname(customer.getSurname());

            if (custom.getEmployeeId() != null) {
                EmployeeMyISAM employee = employeeMyIsamRepository.getEmployeeById(custom.getEmployeeId());
                customDTO.setEmployeeId(employee.getId());
                customDTO.setEmployeeName(employee.getName());
                customDTO.setEmployeeSurname(employee.getSurname());
            }

            customDTO.setStatus(custom.getStatus().toString());
            customDTO.setDepartment(departmentMyIsamRepository.getDepartmentNameById(custom.getDepartmentId()));
            customDTO.setCustomProductDTOList(customProductMyIsamRepository.getCustomProductDTOListByCustomId(custom.getId()));

            allCustomsDTOs.add(customDTO);
        }
        return allCustomsDTOs;
    }
}
