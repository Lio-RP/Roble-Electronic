package com.roble.springproject.robleelectronic.repositories;

import com.roble.springproject.robleelectronic.models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
