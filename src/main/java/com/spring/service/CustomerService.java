package com.spring.service;

import com.spring.entity.Customer;
import com.spring.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CustomerService {

    public Customer saveCustomer(Customer customer);
    public Customer getCustomer(long id);
    public List<Customer> customerList();
    public Customer updateCustomer(Customer customer, Long id);
    public Map<String, Boolean> deleteCustomer(long id);
}
