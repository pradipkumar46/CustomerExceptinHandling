package com.spring.controller;

import com.spring.entity.Customer;
import com.spring.exception.BusinessException;
import com.spring.exception.CustomerExceptionController;
import com.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer){
        try {
            Customer saveCustomer = customerService.saveCustomer(customer);
            return new ResponseEntity<Customer>(saveCustomer, HttpStatus.CREATED);
        }catch (BusinessException ex){
            CustomerExceptionController ce = new CustomerExceptionController(ex.getErrorCode(), ex.getErrorMessage());
            return new ResponseEntity<CustomerExceptionController>(ce, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            CustomerExceptionController ce = new CustomerExceptionController("611", "Something went wrong in controller");
            return new ResponseEntity<CustomerExceptionController>(ce, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getcustomer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable long id){
        try{
          Customer customer =  customerService.getCustomer(id);
          return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
        }catch (BusinessException ex){
            CustomerExceptionController ce = new CustomerExceptionController(ex.getErrorCode(), ex.getErrorMessage());
            return new ResponseEntity<CustomerExceptionController>(ce, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            CustomerExceptionController ce = new CustomerExceptionController("612", "Something went wrong in get customer details "+id);
            return new ResponseEntity<CustomerExceptionController>(ce, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/customerList")
    public List<Customer> getCustomerList(){
        try {
         List<Customer> customerList = customerService.customerList();
         if(customerList.isEmpty())
             throw new BusinessException("613", "Hey list completely empty we have nothing to do ");
         return customerList;
        }catch (Exception ex){
            throw new BusinessException("614", "Customer list is empty data is not prenent in db ");
        }

    }
    @PutMapping("/update/{id}")
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable long id){
        return customerService.updateCustomer(customer, id);
    }
    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable long id){
        return customerService.deleteCustomer(id);
    }
}
