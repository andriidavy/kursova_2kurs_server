package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.product.Product;
import com.example.WarehouseDatabaseJava.model.product.ProductRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import com.example.WarehouseDatabaseJava.model.users.customer.CustomerRepository;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProduct;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProductRepository;
import com.example.WarehouseDatabaseJava.model.users.employee.Employee;
import com.example.WarehouseDatabaseJava.model.users.employee.EmployeeRepository;
import com.example.WarehouseDatabaseJava.model.users.manager.Manager;
import com.example.WarehouseDatabaseJava.model.users.manager.ManagerRepository;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.Department;
import com.example.WarehouseDatabaseJava.model.users.manager.stage.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomService {
    @Autowired
    private CustomRepository customRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomProductRepository customProductRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    //Створення замовлення покупцем (TESTED!)
    //ОБНОВА!!!
    @Transactional
    public String createCustom(String customerId, String departmentId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer not found with id:" + customerId);
        }
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department not found with id:" + departmentId);
        }

        Customer customer = customerRepository.getReferenceById(customerId);
        Department department = departmentRepository.getReferenceById(departmentId);

        if (customer.getCart() == null) {
            throw new NullPointerException("Cart for customer with id " + customerId + " is not be created");
        }

        if (!customer.getCart().getCartProductList().isEmpty()) {
            // Проверяем наличие достаточного количества продукта в базе данных
            for (CartProduct cartProduct : customer.getCart().getCartProductList()) {
                Product product = cartProduct.getProduct();
                int quantityInCart = cartProduct.getQuantity();
                int quantityInStock = product.getQuantity();
                if (quantityInStock < quantityInCart) {
                    throw new RuntimeException("Not enough stock for product: " + product.getBarcode());
                }
            }
            Custom custom = new Custom();
            custom.setStatus(Custom.Status.CREATED);
            custom.setDepartment(department);
            custom.setCustomer(customer); // Установить связь с заказчиком для заказа (обратка неявная бо каскад all)

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
            custom.setPrice(customer.getCart().getPrice());
            customRepository.save(custom);

            cartProductRepository.deleteAll(customer.getCart().getCartProductList());
            customer.getCart().getCartProductList().clear();
            customer.getCart().setPrice(0);
            return custom.getId(); //повертаємо значення id новоствореного замовлення
        } else {
            return "-1";
        }
    }

    // Отримання списку замовлень для конкретного покупця(по його id) TESTED
// (повертає об'єкт DTO з всіма потрібними параметрами)

    //ОБНОВА!!!
    public List<CustomDTO> getCustomsForCustomer(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer not found with id:" + customerId);
        }

        Customer customer = customerRepository.getReferenceById(customerId);
        List<Custom> customs = customer.getCustomList();
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (Custom custom : customs) {
            CustomDTO customDTO = new CustomDTO();
            customDTO.setCustomId(custom.getId());
            customDTO.setCustomerId(customer.getId());
            customDTO.setCustomerName(customer.getName());
            customDTO.setCustomerSurname(customer.getSurname());
            customDTO.setPrice(custom.getPrice());
            customDTO.setStatus(custom.getStatus().toString());

            Department department = custom.getDepartment();
            if (department != null) {
                customDTO.setDepartment(department.getDepartmentName());
            }

            List<CustomProductDTO> customProductDTOs = setListCustomProductDTO(custom);
            customDTO.setCustomProductDTOList(customProductDTOs);
            customDTOs.add(customDTO);
        }

        return customDTOs;
    }

// Отримання списку актуальних призначених замовлень зі статусом IN_PROCESSING для конкретного робітника(по його id)
    //(повертає об'єкт DTO з всіма потрібними параметрами)

    //ОБНОВА!!!
    public List<CustomDTO> getProcessingCustomsForEmployee(String employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id:" + employeeId);
        }

        Employee employee = employeeRepository.getReferenceById(employeeId);
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (Custom custom : employee.getCustomList()) {
            if (custom.getStatus() == Custom.Status.IN_PROCESSING || custom.getStatus() == Custom.Status.WAITING_RESPONSE) {
                CustomDTO customDTO = new CustomDTO();
                customDTO.setCustomId(custom.getId());

                Customer customer = custom.getCustomer();
                customDTO.setCustomerId(customer.getId());
                customDTO.setCustomerName(customer.getName());
                customDTO.setCustomerSurname(customer.getSurname());

                customDTO.setStatus(custom.getStatus().toString());

                List<CustomProductDTO> customProductDTOs = setListCustomProductDTO(custom);
                customDTO.setCustomProductDTOList(customProductDTOs);
                customDTOs.add(customDTO);
            }
        }

        return customDTOs;
    }

    // Отримання списку виконаних призначених замовлень зі статусом PROCESSED для конкретного робітника(по його id)
    //(повертає об'єкт DTO з всіма потрібними параметрами)

    //ОБНОВА!!!
    public List<CustomDTO> getProcessedCustomsForEmployee(String employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id:" + employeeId);
        }

        Employee employee = employeeRepository.getReferenceById(employeeId);
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (Custom custom : employee.getCustomList()) {
            if (custom.getStatus() == Custom.Status.PROCESSED) {
                CustomDTO customDTO = new CustomDTO();
                customDTO.setCustomId(custom.getId());

                Customer customer = custom.getCustomer();
                customDTO.setCustomerId(customer.getId());
                customDTO.setCustomerName(customer.getName());
                customDTO.setCustomerSurname(customer.getSurname());

                customDTO.setStatus(custom.getStatus().toString());

                List<CustomProductDTO> customProductDTOs = setListCustomProductDTO(custom);
                customDTO.setCustomProductDTOList(customProductDTOs);
                customDTOs.add(customDTO);
            }
        }

        return customDTOs;
    }

    // Отримання всіх створених замовлень зі статусом CREATED (Для Manager`a)
    // (повертає об'єкт DTO з всіма потрібними параметрами)

    //+ враховує відділ замовлення і відділи менеджера TESTED
    //ОБНОВА!!!
    public List<CustomDTO> getAllCreatedCustoms(String managerId) {
        if (!managerRepository.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id:" + managerId);
        }

        Manager manager = managerRepository.getReferenceById(managerId);
        List<Department> managerDepartments = manager.getDepartmentList();

        List<Custom> allCreatedCustoms = customRepository.findAllByStatus(Custom.Status.CREATED);
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (Custom custom : allCreatedCustoms) {
            Department department = custom.getDepartment();
            if (department != null && managerDepartments.contains(department)) {
                CustomDTO customDTO = new CustomDTO();
                customDTO.setCustomId(custom.getId());
                customDTO.setStatus(custom.getStatus().toString());
                customDTO.setDepartment(department.getDepartmentName());

                List<CustomProductDTO> customProductDTOs = setListCustomProductDTO(custom);
                customDTO.setCustomProductDTOList(customProductDTOs);
                customDTOs.add(customDTO);
            }
        }
        return customDTOs;
    }

    // Отримання всіх замовлень з повним переліком данних про покупця і робітника що стосуються данного замовлення(Для Manager`a)
    // (повертає об'єкт DTO з всіма потрібними параметрами)
    //ОБНОВА!!!

    public List<CustomDTO> getAllCustoms() {
        List<Custom> allCustoms = customRepository.findAll();
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (Custom custom : allCustoms) {
            //встановлення інфи про замовлення
            CustomDTO customDTO = setCustomDTO(custom);

            //встановлення інфи про продукти що входять до замовлення
            List<CustomProductDTO> customProductDTOs = setListCustomProductDTO(custom);

            //приєднання інфи про продукти замовлення до інфи про замовлення
            customDTO.setCustomProductDTOList(customProductDTOs);

            //додавання готового об'єкту інфи про замовлення до списку інф замовлень
            customDTOs.add(customDTO);
        }

        return customDTOs;
    }

    // Отримання замовлень з повним переліком данних для певної дати створення цього замовлення
    public List<CustomDTO> getAllCustomsByDate(Date creationTime) {
        List<Custom> allCustoms = customRepository.findAllByCreationTime(creationTime);
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (Custom custom : allCustoms) {
            //встановлення інфи про замовлення
            CustomDTO customDTO = setCustomDTO(custom);

            //встановлення інфи про продукти що входять до замовлення
            List<CustomProductDTO> customProductDTOs = setListCustomProductDTO(custom);

            //приєднання інфи про продукти замовлення до інфи про замовлення
            customDTO.setCustomProductDTOList(customProductDTOs);

            //додавання готового об'єкту інфи про замовлення до списку інф замовлень
            customDTOs.add(customDTO);
        }

        return customDTOs;
    }

    // Призначення замовлення на виконання конкретному робітнику (Для Manager`a) TESTED
    //ОБНОВА!!!
    public void assignEmployeeToCustom(String customId, String employeeId) {
        if (!customRepository.existsById(customId)) {
            throw new EntityNotFoundException("Custom not found with id:" + customId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id:" + employeeId);
        }

        Custom custom = customRepository.getReferenceById(customId);
        Employee employee = employeeRepository.getReferenceById(employeeId);

        custom.setEmployee(employee);
        custom.setStatus(Custom.Status.IN_PROCESSING);
        customRepository.save(custom);
    }

    // Встановлення для замовлення статусу PROCESSED(виконаний) TESTED
    @Transactional
    public void setCustomProcessed(String customId) {
        if (!customRepository.existsById(customId)) {
            throw new EntityNotFoundException("Custom not found with id:" + customId);
        }

        Custom custom = customRepository.getReferenceById(customId);

        if (custom.getStatus() != Custom.Status.WAITING_RESPONSE) {
            throw new IllegalStateException("Custom with id " + customId + " cannot be set to WAITING_RESPONSE status because it is not in IN_PROCESSING status");
        }
        custom.setStatus(Custom.Status.PROCESSED);
        customRepository.save(custom);
    }

    @Transactional
    public void setCustomInProcessing(String customId) {
        if (!customRepository.existsById(customId)) {
            throw new EntityNotFoundException("Custom not found with id:" + customId);
        }

        Custom custom = customRepository.getReferenceById(customId);

        if (custom.getStatus() != Custom.Status.WAITING_RESPONSE) {
            throw new IllegalStateException("Custom with id " + customId + " cannot be set to IN_PROCESSING status because it is not in WAITING_RESPONSE status");
        }
        custom.setStatus(Custom.Status.IN_PROCESSING);
        customRepository.save(custom);
    }

    // Встановлення для готового замовлення статусу SENT(відправлений) TESTED
    public void setCustomSent(String customId) {
        if (!customRepository.existsById(customId)) {
            throw new EntityNotFoundException("Custom not found with id:" + customId);
        }

        Custom custom = customRepository.getReferenceById(customId);

        if (custom.getStatus() != Custom.Status.PROCESSED) {
            throw new IllegalStateException("Custom with id " + customId + " cannot be set to SENT status because it is not in PROCESSED status");
        }
        custom.setStatus(Custom.Status.SENT);
        customRepository.save(custom);
    }

    //внутрішній метод для встановлення списку продуктів(DTO!) для певного замовлення Custom
    private List<CustomProductDTO> setListCustomProductDTO(Custom custom) {
        List<CustomProductDTO> customProductDTOs = new ArrayList<>();
        for (CustomProduct customProduct : custom.getCustomProductList()) {
            Product product = customProduct.getProduct();

            CustomProductDTO customProductDTO = new CustomProductDTO();
            customProductDTO.setProductBarcode(product.getBarcode());
            customProductDTO.setProductName(product.getName());
            customProductDTO.setQuantity(customProduct.getQuantity());

            customProductDTOs.add(customProductDTO);
        }
        return customProductDTOs;
    }

    //внутрішній метод для встановлення певного замовлення Custom (DTO!) (вся інфа про замовлення)
    private CustomDTO setCustomDTO(Custom custom) {
        CustomDTO customDTO = new CustomDTO();
        customDTO.setCustomId(custom.getId());

        Customer customer = custom.getCustomer();
        customDTO.setCustomerId(customer.getId());
        customDTO.setCustomerName(customer.getName());
        customDTO.setCustomerSurname(customer.getSurname());

        if (custom.getEmployee() != null) {
            Employee employee = custom.getEmployee();
            customDTO.setEmployeeId(employee.getId());
            customDTO.setEmployeeName(employee.getName());
            customDTO.setEmployeeSurname(employee.getSurname());
        }

        customDTO.setStatus(custom.getStatus().toString());
        customDTO.setPrice(custom.getPrice());
        customDTO.setCreatingTime(custom.getCreationTime());

        if (custom.getDepartment() != null) {
            Department department = custom.getDepartment();
            customDTO.setDepartment(department.getDepartmentName());
        }
        return customDTO;
    }
}
