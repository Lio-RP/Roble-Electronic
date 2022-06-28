package com.roble.springproject.RobleElectronic.services;

import com.roble.springproject.RobleElectronic.models.Customer;
import com.roble.springproject.RobleElectronic.models.User;

public interface CustomerService {

    Customer save(Customer customer, User user);
}
