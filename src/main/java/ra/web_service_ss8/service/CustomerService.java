package ra.web_service_ss8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.web_service_ss8.model.entity.Customer;
import ra.web_service_ss8.model.entity.Employee;
import ra.web_service_ss8.repository.CustomerRepository;
import ra.web_service_ss8.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {

        customer.setStatus(true);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setFullName(updatedCustomer.getFullName());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setNumberOfPayments(updatedCustomer.getNumberOfPayments());
        return customerRepository.save(customer);
    }

    public void softDeleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setStatus(false);
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}