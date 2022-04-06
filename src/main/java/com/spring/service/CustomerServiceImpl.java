package com.spring.service;

import com.spring.entity.Customer;
import com.spring.exception.BusinessException;
import com.spring.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.RejectedExecutionException;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        try {
            if(customer.getName().isEmpty() || customer.getName().length() ==0){
                throw new BusinessException("601","Please send proper name it is blank ");
            }
            Customer saveCustomer = customerRepository.save(customer);
            return saveCustomer;
        }catch (IllegalArgumentException ex){
            throw new BusinessException("602","Given Customer is null "+ex.getMessage());
        }catch (Exception ex){
            throw new BusinessException("603", "Something went worng in service layer ");
        }
    }

    @Override
    public Customer getCustomer(long id) {
        try {
            Customer customer = customerRepository.findById(id).get();
            return customer;
        }catch (IllegalArgumentException ex){
            throw new BusinessException("606", "Given customer is null please send valid customer id ");
        }catch (NoSuchElementException ex){
            throw new BusinessException("607", "Given Customer id is nut valid ");
        }
    }

    @Override
    public List<Customer> customerList() {
        try {
            List<Customer> customerList = customerRepository.findAll();
            if (customerList.isEmpty())
                throw new BusinessException("604", "Hay the list of customer is empty");
            return customerList;
        } catch (Exception ex) {
            throw new BusinessException("605", "Something went worng in service layer while fetching data ");
        }
    }

    @Override
    public Customer updateCustomer(Customer customer, Long id) {
        try {
            return customerRepository.findById(id).map(c -> {
                c.setName(customer.getName());
                c.setAddress(customer.getAddress());
                c.setEmail(customer.getEmail());
                c.setAge(customer.getAge());
                return customerRepository.save(c);
            }).orElseGet(() -> {
                customer.setId(id);
                return customerRepository.save(customer);
            });
        }catch (BusinessException ex){
            throw new BusinessException("608", "Customer data is not valid ");
        }catch (Exception ex){
            throw new BusinessException("609", "Customer data is not valid please provide valid data ");
        }
    }

    @Override
    public Map<String, Boolean> deleteCustomer(long id) {
        try{
            customerRepository.findById(id).orElseThrow(()-> new RejectedExecutionException("Customer not found from customer id "+id));
            customerRepository.deleteById(id);
        }catch (IllegalArgumentException ex){
            throw new BusinessException("610", "Given customer id is null please provide valid id for delete data "+ex.getMessage());
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("Customer Deleted ", Boolean.TRUE);
        return response;
    }
}
