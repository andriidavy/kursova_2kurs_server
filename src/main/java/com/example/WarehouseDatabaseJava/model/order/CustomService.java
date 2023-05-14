package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerCustom;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerCustomRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProduct;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProductRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartRepository;
import com.example.WarehouseDatabaseJava.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeCustom;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private CartRepository cartRepository;

    //Створення замовлення покупцем
    @Transactional
    public void createCustom(int customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        if (customer.getCart() != null && !customer.getCart().getCartProductList().isEmpty()) {
            // Проверяем наличие достаточного количества продукта в базе данных
            for (CartProduct cartProduct : customer.getCart().getCartProductList()) {
                Product product = cartProduct.getProduct();
                int quantityInCart = cartProduct.getQuantity();
                int quantityInStock = product.getQuantity();
                if (quantityInStock < quantityInCart) {
                    throw new RuntimeException("Not enough stock for product: " + product.getName());
                }
            }
            Custom custom = new Custom();
            customRepository.save(custom);
            custom.setStatus(Custom.Status.CREATED);
            CustomerCustom customerCustom = new CustomerCustom();
            customerCustom.setCustomer(customer);
            customerCustom.setCustom(custom);
            customerCustomRepository.save(customerCustom);
            for (CartProduct cartProduct : customer.getCart().getCartProductList()) {
                Product product = cartProduct.getProduct();
                int quantityInCart = cartProduct.getQuantity();
                int quantityInStock = product.getQuantity();
                // Обновляем количество продукта в базе данных
                product.setQuantity(quantityInStock - quantityInCart);
                    productRepository.save(product);
                CustomProduct customProduct = new CustomProduct();
                customProduct.setProduct(product);
                customProduct.setQuantity(quantityInCart);
                customProduct.setCustom(custom);
                customProductRepository.save(customProduct);
            }
            cartProductRepository.deleteAll(customer.getCart().getCartProductList());
            customer.getCart().getCartProductList().clear();
        }
    }

    // Отримання списку замовлень для конкретного покупця(по його id)
    public List<Custom> getCustomsForCustomer(int customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        List<Custom> customs = new ArrayList<>();
        for (CustomerCustom customerCustom : customer.getCustomerCustomList()) {
            customs.add(customerCustom.getCustom());
        }
        return customs;
    }

    //    // Отримання списку призначених замовлень для конкретного робітника(по його id) (Для Employee`a)
//    public List<Custom> getCustomsForEmployee(int employeeId){
//        Employee employee = employeeRepository.getReferenceById(employeeId);
//        List<Custom> customs = new ArrayList<>();
//        for (EmployeeCustom employeeCustom : employee.getEmployeeCustomList()) {
//            customs.add(employeeCustom.getCustom());
//        }
//        return customs;
//    }

// Отримання списку актуальних призначених замовлень зі статусом IN_PROCESSING для конкретного робітника(по його id)
    public List<Custom> getProcessingCustomsForEmployee(int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        List<Custom> customs = new ArrayList<>();
        for (EmployeeCustom employeeCustom : employee.getEmployeeCustomList()) {
            Custom custom = employeeCustom.getCustom();
            if (custom.getStatus() == Custom.Status.IN_PROCESSING) {
                customs.add(custom);
            }
        }
        return customs;
    }

    // Отримання списку виконаних призначених замовлень зі статусом PROCESSED для конкретного робітника(по його id)
    public List<Custom> getProcessedCustomsForEmployee(int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        List<Custom> customs = new ArrayList<>();
        for (EmployeeCustom employeeCustom : employee.getEmployeeCustomList()) {
            Custom custom = employeeCustom.getCustom();
            if (custom.getStatus() == Custom.Status.PROCESSED) {
                customs.add(custom);
            }
        }
        return customs;
    }

//    // Отримання списку всіх замовлень від всіх покупців (Для Manager'а)
//    public List<Custom> getAllCustoms() {
//        return customRepository.findAll();
//    }

    // Отримання всіх створених замовлень зі статусом CREATED (Для Manager`a)
    public List<Custom> getAllCreatedCustoms() {
        List<Custom> allCreatedCustoms = customRepository.findAll();
        return allCreatedCustoms.stream()
                .filter(custom -> custom.getStatus()== Custom.Status.CREATED)
                .collect(Collectors.toList());
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
            customRepository.save(custom);
            custom.setStatus(Custom.Status.IN_PROCESSING);
        }
    }
// Встановлення для замовлення статусу PROCESSED(виконаний)
    @Transactional
    public void setCustomProcessed(int customId) {
        Custom custom = customRepository.getReferenceById(customId);
        if (custom == null) {
            throw new EntityNotFoundException("Custom with id " + customId + " not found");
        }
        if (custom.getStatus() != Custom.Status.IN_PROCESSING) {
            throw new IllegalStateException("Custom with id " + customId + " cannot be set to PROCESSED status because it is not in IN_PROCESSING status");
        }
        custom.setStatus(Custom.Status.PROCESSED);
        customRepository.save(custom);
    }

    // Встановлення для готового замовлення статусу SENT(відправлений)
    public void setCustomSent(int customId) {
        Custom custom = customRepository.getReferenceById(customId);
        if (custom == null) {
            throw new EntityNotFoundException("Custom with id " + customId + " not found");
        }
        if (custom.getStatus() != Custom.Status.PROCESSED) {
            throw new IllegalStateException("Custom with id " + customId + " cannot be set to SENT status because it is not in PROCESSED status");
        }
        custom.setStatus(Custom.Status.SENT);
        customRepository.save(custom);
    }
}



//ПОПЕРЕДНЯ РЕАЛІЗАЦІЯ МЕТОДУ CREATECUSTOM ЯКА НЕ ВРАХОВУЄ КІЛЬКІСТЬ ТОВАРУ
//    {
//        Customer customer = customerRepository.getReferenceById(customerId);
//        if (customer.getCart() != null && !customer.getCart().getCartProductList().isEmpty()) {
//            Custom custom = new Custom();
//            customRepository.save(custom);
//            custom.setStatus(Custom.Status.CREATED);
//            CustomerCustom customerCustom = new CustomerCustom();
//            customerCustom.setCustomer(customer);
//            customerCustom.setCustom(custom);
//            customerCustomRepository.save(customerCustom);
//
//            for (CartProduct cartProduct : customer.getCart().getCartProductList()) {
//                CustomProduct customProduct = new CustomProduct();
//                customProduct.setProduct(cartProduct.getProduct());
//                customProduct.setQuantity(cartProduct.getQuantity());
//                customProduct.setCustom(custom);
//                customProductRepository.save(customProduct);
//            }
//
//            customer.getCart().getCartProductList().clear();
//        }
//    }
