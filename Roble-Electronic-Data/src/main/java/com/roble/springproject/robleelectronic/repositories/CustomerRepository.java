package com.roble.springproject.RobleElectronic.repositories;

import com.roble.springproject.RobleElectronic.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
