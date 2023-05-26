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
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProductDTO;
import com.example.WarehouseDatabaseJava.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeCustom;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeRepository;
import com.example.WarehouseDatabaseJava.model.users.manager.Manager;
import com.example.WarehouseDatabaseJava.model.users.manager.ManagerRepository;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.Department;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentCustom;
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

    @Autowired
    private ManagerRepository managerRepository;

    //Створення замовлення покупцем TESTED
    @Transactional
    public int createCustom(int customerId) {
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
            return custom.getId(); //повертаємо значення id новоствореного замовлення
        }
        return -1;  // Возвращаем -1 в случае, если заказ не был создан
    }

    // Отримання списку замовлень для конкретного покупця(по його id) TESTED
// (повертає об'єкт DTO з всіма потрібними параметрами)
    public List<CustomDTO> getCustomsForCustomer(int customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);

        List<CustomerCustom> customerCustoms = customer.getCustomerCustomList();
        List<CustomDTO> customDTOs = new ArrayList<>();

        CustomDTO customDTO = null;

        for (CustomerCustom customerCustom : customerCustoms) {
            Custom custom = customerCustom.getCustom();

            // Проверяем, существует ли уже CustomDTO для данного Custom
            if (customDTO == null || customDTO.getCustomId() != custom.getId()) {
                customDTO = new CustomDTO();
                customDTO.setCustomId(custom.getId());
                customDTO.setCustomerId(customer.getId());
                customDTO.setCustomerName(customer.getName());
                customDTO.setCustomerSurname(customer.getSurname());
                customDTO.setStatus(custom.getStatus().toString());
                customDTO.setCustomProductDTOList(new ArrayList<>());
                customDTOs.add(customDTO);
            }

            DepartmentCustom departmentCustom = custom.getDepartmentCustomList().stream().findFirst().orElse(null);
            if (departmentCustom != null) {
                Department department = departmentCustom.getDepartment();
                customDTO.setDepartment(department.getDepartmentName());
            }


            for (CustomProduct customProduct : custom.getCustomProductList()) {
                Product product = customProduct.getProduct();
                int quantity = customProduct.getQuantity();

                CustomProductDTO customProductDTO = new CustomProductDTO();
                customProductDTO.setProductId(product.getId());
                customProductDTO.setProductName(product.getName());
                customProductDTO.setQuantity(quantity);

                customDTO.getCustomProductDTOList().add(customProductDTO);
            }
        }

        return customDTOs;
    }


// Отримання списку актуальних призначених замовлень зі статусом IN_PROCESSING для конкретного робітника(по його id)
    //(повертає об'єкт DTO з всіма потрібними параметрами)

    public List<CustomDTO> getProcessingCustomsForEmployee(int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (EmployeeCustom employeeCustom : employee.getEmployeeCustomList()) {
            Custom custom = employeeCustom.getCustom();

            if (custom.getStatus() == Custom.Status.IN_PROCESSING || custom.getStatus() == Custom.Status.WAITING_RESPONSE) {
                CustomDTO customDTO = new CustomDTO();
                customDTO.setCustomId(custom.getId());

                Customer customer = getCustomerByCustom(custom);
                customDTO.setCustomerId(customer.getId());
                customDTO.setCustomerName(customer.getName());
                customDTO.setCustomerSurname(customer.getSurname());

                customDTO.setStatus(custom.getStatus().toString());

                List<CustomProductDTO> customProductDTOs = new ArrayList<>();
                for (CustomProduct customProduct : custom.getCustomProductList()) {
                    Product product = customProduct.getProduct();
                    int quantity = customProduct.getQuantity();

                    CustomProductDTO customProductDTO = new CustomProductDTO();
                    customProductDTO.setProductId(product.getId());
                    customProductDTO.setProductName(product.getName());
                    customProductDTO.setQuantity(quantity);

                    customProductDTOs.add(customProductDTO);
                }

                customDTO.setCustomProductDTOList(customProductDTOs);
                customDTOs.add(customDTO);
            }
        }

        return customDTOs;
    }

    // Отримання списку виконаних призначених замовлень зі статусом PROCESSED для конкретного робітника(по його id)
    //(повертає об'єкт DTO з всіма потрібними параметрами)
    public List<CustomDTO> getProcessedCustomsForEmployee(int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (EmployeeCustom employeeCustom : employee.getEmployeeCustomList()) {
            Custom custom = employeeCustom.getCustom();

            if (custom.getStatus() == Custom.Status.PROCESSED) {
                CustomDTO customDTO = new CustomDTO();
                customDTO.setCustomId(custom.getId());

                Customer customer = getCustomerByCustom(custom);
                customDTO.setCustomerId(customer.getId());
                customDTO.setCustomerName(customer.getName());
                customDTO.setCustomerSurname(customer.getSurname());

                customDTO.setStatus(custom.getStatus().toString());

                List<CustomProductDTO> customProductDTOs = new ArrayList<>();
                for (CustomProduct customProduct : custom.getCustomProductList()) {
                    Product product = customProduct.getProduct();
                    int quantity = customProduct.getQuantity();

                    CustomProductDTO customProductDTO = new CustomProductDTO();
                    customProductDTO.setProductId(product.getId());
                    customProductDTO.setProductName(product.getName());
                    customProductDTO.setQuantity(quantity);

                    customProductDTOs.add(customProductDTO);
                }

                customDTO.setCustomProductDTOList(customProductDTOs);
                customDTOs.add(customDTO);
            }
        }

        return customDTOs;
    }

    // Отримання всіх створених замовлень зі статусом CREATED (Для Manager`a)
    // (повертає об'єкт DTO з всіма потрібними параметрами)

    //+ враховує відділ замовлення і відділи менеджера TESTED

    public List<CustomDTO> getAllCreatedCustoms(int managerId) {
        Manager manager = managerRepository.getReferenceById(managerId);
        List<Department> managerDepartments = manager.getDepartmentList();

        List<Custom> allCreatedCustoms = customRepository.findAllByStatus(Custom.Status.CREATED);
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (Custom custom : allCreatedCustoms) {
            List<DepartmentCustom> departmentCustoms = custom.getDepartmentCustomList();
            Department matchingDepartment = null;

            for (DepartmentCustom departmentCustom : departmentCustoms) {
                Department department = departmentCustom.getDepartment();

                if (managerDepartments.contains(department)) {
                    matchingDepartment = department;
                    break;
                }
            }

            if (matchingDepartment != null) {
                CustomDTO customDTO = new CustomDTO();
                customDTO.setCustomId(custom.getId());

                customDTO.setStatus(custom.getStatus().toString());
                customDTO.setDepartment(matchingDepartment.getDepartmentName());

                List<CustomProductDTO> customProductDTOs = new ArrayList<>();
                for (CustomProduct customProduct : custom.getCustomProductList()) {
                    Product product = customProduct.getProduct();
                    int quantity = customProduct.getQuantity();

                    CustomProductDTO customProductDTO = new CustomProductDTO();
                    customProductDTO.setProductId(product.getId());
                    customProductDTO.setProductName(product.getName());
                    customProductDTO.setQuantity(quantity);

                    customProductDTOs.add(customProductDTO);
                }

                customDTO.setCustomProductDTOList(customProductDTOs);
                customDTOs.add(customDTO);
            }
        }

        return customDTOs;
    }

    //ПОПЕРЕДНЯ РОБОЧА РЕАЛІЗАЦІЯ БЕЗ ФІЧІ З ВІДДІЛАМИ
//    public List<CustomDTO> getAllCreatedCustoms() {
//        List<Custom> allCreatedCustoms = customRepository.findAll();
//        List<CustomDTO> customDTOs = new ArrayList<>();
//
//        for (Custom custom : allCreatedCustoms) {
//            if (custom.getStatus() == Custom.Status.CREATED) {
//                CustomDTO customDTO = new CustomDTO();
//                customDTO.setCustomId(custom.getId());
//
//                customDTO.setStatus(custom.getStatus().toString());
//
//                List<CustomProductDTO> customProductDTOs = new ArrayList<>();
//                for (CustomProduct customProduct : custom.getCustomProductList()) {
//                    Product product = customProduct.getProduct();
//                    int quantity = customProduct.getQuantity();
//
//                    CustomProductDTO customProductDTO = new CustomProductDTO();
//                    customProductDTO.setProductId(product.getId());
//                    customProductDTO.setProductName(product.getName());
//                    customProductDTO.setQuantity(quantity);
//
//                    customProductDTOs.add(customProductDTO);
//                }
//
//                customDTO.setCustomProductDTOList(customProductDTOs);
//                customDTOs.add(customDTO);
//            }
//        }
//
//        return customDTOs;
//    }


    // Отримання всіх замовлень з повним переліком данних про покупця і робітника що стосуються данного замовлення(Для Manager`a)
    // (повертає об'єкт DTO з всіма потрібними параметрами)
    public List<CustomDTO> getAllCustoms() {
        List<Custom> allCreatedCustoms = customRepository.findAll();
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (Custom custom : allCreatedCustoms) {
            CustomDTO customDTO = new CustomDTO();
            customDTO.setCustomId(custom.getId());

            Customer customer = getCustomerByCustom(custom);
            customDTO.setCustomerId(customer.getId());
            customDTO.setCustomerName(customer.getName());
            customDTO.setCustomerSurname(customer.getSurname());

            if (custom.getStatus() != Custom.Status.CREATED) {
                Employee employee = getEmployeeByCustom(custom);
                customDTO.setEmployeeId(employee.getId());
                customDTO.setEmployeeName(employee.getName());
                customDTO.setEmployeeSurname(employee.getSurname());
            }

            customDTO.setStatus(custom.getStatus().toString());

            DepartmentCustom departmentCustom = custom.getDepartmentCustomList().stream().findFirst().orElse(null);
            if (departmentCustom != null) {
                Department department = departmentCustom.getDepartment();
                customDTO.setDepartment(department.getDepartmentName());
            }

            List<CustomProductDTO> customProductDTOs = new ArrayList<>();
            for (CustomProduct customProduct : custom.getCustomProductList()) {
                Product product = customProduct.getProduct();
                int quantity = customProduct.getQuantity();

                CustomProductDTO customProductDTO = new CustomProductDTO();
                customProductDTO.setProductId(product.getId());
                customProductDTO.setProductName(product.getName());
                customProductDTO.setQuantity(quantity);

                customProductDTOs.add(customProductDTO);
            }

            customDTO.setCustomProductDTOList(customProductDTOs);
            customDTOs.add(customDTO);
        }

        return customDTOs;
    }


    // Призначення замовлення на виконання конкретному робітнику (Для Manager`a) TESTED
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
            custom.setStatus(Custom.Status.IN_PROCESSING);
            customRepository.save(custom);
        }
    }

    // Встановлення для замовлення статусу PROCESSED(виконаний) TESTED
    @Transactional
    public void setCustomProcessed(int customId) {
        Custom custom = customRepository.getReferenceById(customId);
        if (custom == null) {
            throw new EntityNotFoundException("Custom with id " + customId + " not found");
        }
        if (custom.getStatus() != Custom.Status.WAITING_RESPONSE) {
            throw new IllegalStateException("Custom with id " + customId + " cannot be set to WAITING_RESPONSE status because it is not in IN_PROCESSING status");
        }
        custom.setStatus(Custom.Status.PROCESSED);
        customRepository.save(custom);
    }

    //Встановлення для замовлення статусу WAITING_RESPONSE(очікує відповіді менеджера)
    @Transactional
    public void setCustomWaitingResponse(int customId) {
        Custom custom = customRepository.getReferenceById(customId);
        if (custom == null) {
            throw new EntityNotFoundException("Custom with id " + customId + " not found");
        }
        if (custom.getStatus() != Custom.Status.IN_PROCESSING) {
            throw new IllegalStateException("Custom with id " + customId + " cannot be set to WAITING_RESPONSE status because it is not in IN_PROCESSING status");
        }
        custom.setStatus(Custom.Status.WAITING_RESPONSE);
        customRepository.save(custom);
    }

    @Transactional
    public void setCustomInProcessing(int customId) {
        Custom custom = customRepository.getReferenceById(customId);
        if (custom == null) {
            throw new EntityNotFoundException("Custom with id " + customId + " not found");
        }
        if (custom.getStatus() != Custom.Status.WAITING_RESPONSE) {
            throw new IllegalStateException("Custom with id " + customId + " cannot be set to IN_PROCESSING status because it is not in WAITING_RESPONSE status");
        }
        custom.setStatus(Custom.Status.IN_PROCESSING);
        customRepository.save(custom);
    }

    // Встановлення для готового замовлення статусу SENT(відправлений) TESTED
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

    // Внутрішній метод, для встановлення покупця по його замовленню
    private Customer getCustomerByCustom(Custom custom) {
        Customer customer = null;

        // Проверяем, что список CustomerCustom не пустой
        if (!custom.getCustomerCustomList().isEmpty()) {
            CustomerCustom customerCustom = custom.getCustomerCustomList().get(0);
            customer = customerCustom.getCustomer();
        }

        return customer;
    }

    // Внутрішній метод, для встановлення робітника для замовлення
    private Employee getEmployeeByCustom(Custom custom) {
        Employee employee = null;

        // Проверяем, что список EmployeeCustom не пустой
        if (!custom.getEmployeeCustomList().isEmpty()) {
            EmployeeCustom employeeCustom = custom.getEmployeeCustomList().get(0);
            employee = employeeCustom.getEmployee();
        }

        return employee;
    }

}
