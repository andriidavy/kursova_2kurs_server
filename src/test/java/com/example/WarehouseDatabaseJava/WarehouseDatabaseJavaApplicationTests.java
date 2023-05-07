package com.example.WarehouseDatabaseJava;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WarehouseDatabaseJavaApplicationTests {

    @Autowired
    private CustomerService customerService;

//        @Test
    void addUserTest() {
        Customer customer = new Customer();
        customer.setName("Helen");
        customer.setSurname("McRory");
        customer.setEmail("helen@gmail.com");
        customer.setPassword("helen123");
        customerService.save(customer);
    }

//    @Test
    void getAllUsers() {
        List<Customer> customerList = customerService.getAllCustomers();
        System.out.println(customerList);
    }

//    @Test
    void deleteUser(){
        List<Customer> customerList = customerService.getAllCustomers();
        for(Customer customer : customerList){
            customerService.delete(customer);
        }
    }

//    @Test
    void deleteById(){
        customerService.deleteById(1);
    }

}
