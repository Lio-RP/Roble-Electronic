package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Role;
import com.roble.springproject.RobleElectronic.repositories.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RoleServiceImplTest {

    @Mock
    RoleRepository roleRepository;

    RoleServiceImpl roleService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    public void findByRole() {
        Role role = new Role();
        role.setId(1L);
        role.setRole("ADMIN");

        when(roleRepository.findByRole(anyString())).thenReturn(role);

        //when
        Role foundRole = roleService.findByRole("ADMIN");

        //then
        assertNotNull(foundRole);
        assertEquals(role.getId(), foundRole.getId());
        verify(roleRepository, times(1)).findByRole(anyString());
    }
}