package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Customer;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.CustomerRepository;
import com.roble.springproject.RobleElectronic.repositories.UserRepository;
import com.roble.springproject.RobleElectronic.services.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Customer save(Customer customer, User user) {
        Customer savedCustomer = customerRepository.save(customer);
        user.setCustomer(savedCustomer);
        User savedUser = userRepository.save(user);
        return savedCustomer;
    }
}
