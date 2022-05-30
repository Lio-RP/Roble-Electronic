package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Customer;
import com.roble.springproject.RobleElectronic.repositories.CustomerRepository;
import com.roble.springproject.RobleElectronic.services.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
