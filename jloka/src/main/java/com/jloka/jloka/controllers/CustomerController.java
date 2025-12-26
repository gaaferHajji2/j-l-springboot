package com.jloka.jloka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jloka.jloka.models.Customer;
import com.jloka.jloka.repositories.CustomerRepository;

@Controller
@RequestMapping(path="/demo")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    
    @PostMapping("/add")
    public @ResponseBody Customer addNewCustomer(@RequestParam String firstName, @RequestParam String lastName) {
        Customer customer = new Customer(firstName, lastName);
        customerRepository.save(customer);
        return customer;
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
