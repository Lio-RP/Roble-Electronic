package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Customer;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.CustomerRepository;
import com.roble.springproject.RobleElectronic.repositories.UserRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    UserRepository userRepository;

    CustomerServiceImpl customerService;

    @org.junit.Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerRepository, userRepository);
    }

    @org.junit.Test
    public void save() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Liban");
        customer.setLastName("Abdullahi");
        customer.setEmail("libanr4243@gmail.com");

        User user = new User();
        user.setId(1L);
        user.setFirstName("liban");
        user.setLastName("Abdullahi");
        user.setEmail("libanr4243@gmail.com");
        user.setUserName("liban");
        user.setPassword("password12345");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(userRepository.save(any(User.class))).thenReturn(user);

        //when
        Customer savedCustomer = customerService.save(customer, user);

        //then
        assertNotNull(savedCustomer);
        assertEquals(Long.valueOf(1L), savedCustomer.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }
}