package com.roble.springproject.RobleElectronic.repositories;

import com.roble.springproject.RobleElectronic.models.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("SELECT role FROM Role role WHERE role.role = :role")
    Role findByRole(@Param("role")String role);
}
