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

    //Створення замовлення покупцем TESTED
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

    // Отримання списку замовлень для конкретного покупця(по його id) TESTED
//    public List<Custom> getCustomsForCustomer(int customerId) {
//        Customer customer = customerRepository.getReferenceById(customerId);
//        List<Custom> customs = new ArrayList<>();
//        for (CustomerCustom customerCustom : customer.getCustomerCustomList()) {
//            customs.add(customerCustom.getCustom());
//        }
//        return customs;
//    }

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
            customDTO.setStatus(custom.getStatus());
            customDTO.setCustomProductDTOList(new ArrayList<>());
            customDTOs.add(customDTO);
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

//    public List<CustomProductDTO> getCustomsForCustomer(int customerId) {
//        Customer customer = customerRepository.getReferenceById(customerId);
//
//        List<CustomerCustom> customerCustoms = customer.getCustomerCustomList();
//        List<CustomProductDTO> customProductDTOS = new ArrayList<>();
//
//        for (CustomerCustom customerCustom : customerCustoms) {
//            Custom custom = customerCustom.getCustom();
//            for (CustomProduct customProduct : custom.getCustomProductList()) {
//                Product product = customProduct.getProduct();
//                int quantity = customProduct.getQuantity();
//
//                CustomProductDTO customProductDTO = new CustomProductDTO();
//                customProductDTO.setCustomId(custom.getId());
//                customProductDTO.setStatus(custom.getStatus());
//                customProductDTO.setProductId(product.getId());
//                customProductDTO.setProductName(product.getName());
//                customProductDTO.setQuantity(quantity);
//
//                customProductDTOS.add(customProductDTO);
//            }
//        }
//
//        return customProductDTOS;
//    }



// Отримання списку актуальних призначених замовлень зі статусом IN_PROCESSING для конкретного робітника(по його id) TESTED
//    public List<Custom> getProcessingCustomsForEmployee(int employeeId) {
//        Employee employee = employeeRepository.getReferenceById(employeeId);
//        List<Custom> customs = new ArrayList<>();
//        for (EmployeeCustom employeeCustom : employee.getEmployeeCustomList()) {
//            Custom custom = employeeCustom.getCustom();
//            if (custom.getStatus() == Custom.Status.IN_PROCESSING) {
//                customs.add(custom);
//            }
//        }
//        return customs;
//    }


// Отримання списку актуальних призначених замовлень зі статусом IN_PROCESSING для конкретного робітника(по його id)
 //(повертає об'єкт DTO з всіма потрібними параметрами)

    public List<CustomDTO> getProcessingCustomsForEmployee(int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (EmployeeCustom employeeCustom : employee.getEmployeeCustomList()) {
            Custom custom = employeeCustom.getCustom();

            if (custom.getStatus() == Custom.Status.IN_PROCESSING) {
                CustomDTO customDTO = new CustomDTO();
                customDTO.setCustomId(custom.getId());

                Customer customer = getCustomerByCustom(custom);
                customDTO.setCustomerId(customer.getId());
                customDTO.setCustomerName(customer.getName());
                customDTO.setCustomerSurname(customer.getSurname());

                customDTO.setStatus(custom.getStatus());

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


//public List<CustomProductDTO> getProcessingCustomsForEmployee(int employeeId) {
//    Employee employee = employeeRepository.getReferenceById(employeeId);
//    List<CustomProductDTO> customProductDTOs = new ArrayList<>();
//    for (EmployeeCustom employeeCustom : employee.getEmployeeCustomList()) {
//        Custom custom = employeeCustom.getCustom();
//        if (custom.getStatus() == Custom.Status.IN_PROCESSING) {
//            for (CustomProduct customProduct : custom.getCustomProductList()) {
//                Product product = customProduct.getProduct();
//                int quantity = customProduct.getQuantity();
//
//                CustomProductDTO customProductDTO = new CustomProductDTO();
//                customProductDTO.setProductId(product.getId());
//                customProductDTO.setProductName(product.getName());
//                customProductDTO.setQuantity(quantity);
//
//                customProductDTOs.add(customProductDTO);
//            }
//        }
//    }
//    return customProductDTOs;
//}

    // Отримання списку виконаних призначених замовлень зі статусом PROCESSED для конкретного робітника(по його id) TESTED
//    public List<Custom> getProcessedCustomsForEmployee(int employeeId) {
//        Employee employee = employeeRepository.getReferenceById(employeeId);
//        List<Custom> customs = new ArrayList<>();
//        for (EmployeeCustom employeeCustom : employee.getEmployeeCustomList()) {
//            Custom custom = employeeCustom.getCustom();
//            if (custom.getStatus() == Custom.Status.PROCESSED) {
//                customs.add(custom);
//            }
//        }
//        return customs;
//    }

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

                customDTO.setStatus(custom.getStatus());

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

    // Отримання всіх створених замовлень зі статусом CREATED (Для Manager`a) TESTED
//    public List<Custom> getAllCreatedCustoms() {
//        List<Custom> allCreatedCustoms = customRepository.findAll();
//        return allCreatedCustoms.stream()
//                .filter(custom -> custom.getStatus()== Custom.Status.CREATED)
//                .collect(Collectors.toList());
//    }

    // Отримання всіх створених замовлень зі статусом CREATED (Для Manager`a)
    // (повертає об'єкт DTO з всіма потрібними параметрами)

    public List<CustomDTO> getAllCreatedCustoms() {
        List<Custom> allCreatedCustoms = customRepository.findAll();
        List<CustomDTO> customDTOs = new ArrayList<>();

        for (Custom custom : allCreatedCustoms) {
            if (custom.getStatus() == Custom.Status.CREATED) {
                CustomDTO customDTO = new CustomDTO();
                customDTO.setCustomId(custom.getId());

                Customer customer = getCustomerByCustom(custom);
                customDTO.setCustomerId(customer.getId());
                customDTO.setCustomerName(customer.getName());
                customDTO.setCustomerSurname(customer.getSurname());

                customDTO.setStatus(custom.getStatus());

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

//    public List<CustomProductDTO> getAllCreatedCustoms() {
//        List<Custom> allCreatedCustoms = customRepository.findAll();
//        List<CustomProductDTO> customProductDTOs = new ArrayList<>();
//        for (Custom custom : allCreatedCustoms) {
//            if (custom.getStatus() == Custom.Status.CREATED) {
//                for (CustomProduct customProduct : custom.getCustomProductList()) {
//                    Product product = customProduct.getProduct();
//                    int quantity = customProduct.getQuantity();
//
//                    CustomProductDTO customProductDTO = new CustomProductDTO();
//                    customProductDTO.setCustomId(custom.getId());
//                    customProductDTO.setStatus(custom.getStatus());
//                    customProductDTO.setProductId(product.getId());
//                    customProductDTO.setProductName(product.getName());
//                    customProductDTO.setQuantity(quantity);
//
//                    customProductDTOs.add(customProductDTO);
//                }
//            }
//        }
//        return customProductDTOs;
//    }


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
        if (custom.getStatus() != Custom.Status.IN_PROCESSING) {
            throw new IllegalStateException("Custom with id " + customId + " cannot be set to PROCESSED status because it is not in IN_PROCESSING status");
        }
        custom.setStatus(Custom.Status.PROCESSED);
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
