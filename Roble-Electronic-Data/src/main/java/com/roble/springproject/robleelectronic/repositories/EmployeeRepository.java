package com.roble.springproject.RobleElectronic.repositories;

import com.roble.springproject.RobleElectronic.models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
