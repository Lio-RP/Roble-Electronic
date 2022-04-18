package com.roble.springproject.robleelectronic.repositories;

import com.roble.springproject.robleelectronic.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
