package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Role;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.UserRepository;
import com.roble.springproject.RobleElectronic.services.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {


    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleService roleService;

    UserServiceImpl userServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userServiceImpl = new UserServiceImpl(userRepository, passwordEncoder, roleService);
    }

    @Test
    public void findByEmail() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Liban");
        user.setLastName("Abdullahi");
        user.setEmail("libanr4243@gmail.com");
        user.setUserName("liban");

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        User foundUser = userServiceImpl.findByEmail(user.getEmail());

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(Long.valueOf(1L), foundUser.getId());
    }

    @Test
    public void register() {

        //Given
        User Unregistering = new User();
        Unregistering.setId(1L);
        Unregistering.setFirstName("Liban");
        Unregistering.setLastName("Abdullahi");
        Unregistering.setEmail("libanr4243@gmail.com");
        Unregistering.setUserName("liban");
        Unregistering.setPassword("password123");

        Role role = new Role();
        role.setId(1L);
        role.setRole("USER");

        when(userRepository.findByUserName(anyString())).thenReturn(null);
        when(roleService.findByRole(anyString())).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(Unregistering);

        //when
        User savedUser = userServiceImpl.register(Unregistering);

        System.out.println(savedUser.getRoles());

        //then
        assertNotNull(savedUser);
        assertEquals(role.getId(), savedUser.getRoles().iterator().next().getId());
        assertNotNull(savedUser.getRoles());
        assertThat(Unregistering.getPassword(), equalTo(savedUser.getPassword()));
        assertEquals(LocalDate.now(), savedUser.getRegDate());

    }

    @Test
    public void emailExists() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Liban");
        user.setLastName("Abdullahi");
        user.setEmail("libanr4243@gmail.com");
        user.setUserName("liban");

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        boolean emailExists = userServiceImpl.emailExists(user.getEmail());

        assertTrue(emailExists);
        verify(userRepository, times(1)).findByEmail(anyString());
    }
}