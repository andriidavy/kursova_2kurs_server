package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerCustom;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerCustomRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProduct;
import com.example.WarehouseDatabaseJava.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeCustom;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomService {
    @Autowired
    private CustomRepository customRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerCustomRepository customerCustomRepository;
    @Autowired
    private CustomProductRepository customProductRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    //Створення замовлення покупцем
    public void createCustom(int customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        if (customer.getCart() != null && !customer.getCart().getCartProductList().isEmpty()) {
            Custom custom = new Custom();
            customRepository.save(custom);
            updateCustomStatus(custom.getId(), Custom.Status.CREATED);
            CustomerCustom customerCustom = new CustomerCustom();
            customerCustom.setCustomer(customer);
            customerCustom.setCustom(custom);
            customerCustomRepository.save(customerCustom);

            for (CartProduct cartProduct : customer.getCart().getCartProductList()) {
                CustomProduct customProduct = new CustomProduct();
                customProduct.setProduct(cartProduct.getProduct());
                customProduct.setQuantity(cartProduct.getQuantity());
                customProduct.setCustom(custom);
                customProductRepository.save(customProduct);
            }

            customer.getCart().getCartProductList().clear();
        }
    }
    // Отримання списку замовлень для конкретного покупця(по його id)
    public List<Custom> getCustomsForCustomer(int customerId){
        Customer customer = customerRepository.getReferenceById(customerId);
        List<Custom> customs = new ArrayList<>();
        for (CustomerCustom customerCustom : customer.getCustomerCustomList()) {
            customs.add(customerCustom.getCustom());
        }
        return customs;
    }

    // Отримання списку призначених замовлень для конкретного робітника(по його id) (Для Employee`a)
    public List<Custom> getCustomsForEmployee(int employeeId){
        Employee employee = employeeRepository.getReferenceById(employeeId);
        List<Custom> customs = new ArrayList<>();
        for (EmployeeCustom employeeCustom : employee.getEmployeeCustomList()) {
            customs.add(employeeCustom.getCustom());
        }
        return customs;
    }

    // Отримання списку всіх замовлень від всіх покупців (Для Manager'а)
    public List<Custom> getAllCustoms(){
        return customRepository.findAll();
    }

    // Призначення замовлення на виконання конкретному робітнику (Для Manager`a)
    public void assignEmployeeToCustom(int customId, int employeeId) {
        Custom custom = customRepository.getReferenceById(customId);
        Employee employee = employeeRepository.getReferenceById(employeeId);

        if (custom != null && employee != null) {

            EmployeeCustom employeeCustom = new EmployeeCustom();
            employeeCustom.setEmployee(employee);
            employeeCustom.setCustom(custom);

            List<EmployeeCustom> employeeCustomList = custom.getEmployeeCustomList();
            employeeCustomList.add(employeeCustom);

            custom.setEmployeeCustomList(employeeCustomList);
            updateCustomStatus(customId, Custom.Status.IN_PROCESSING);
        }
    }
// метод для оновлення статусу замовлення
    public void updateCustomStatus(int customId, Custom.Status newStatus) {
        Custom custom = customRepository.getReferenceById(customId);
        custom.setStatus(newStatus);
        customRepository.save(custom);
    }
}
