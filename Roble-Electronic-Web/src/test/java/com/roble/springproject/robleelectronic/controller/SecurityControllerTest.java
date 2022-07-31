package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.auth.UserDetailsServiceImple;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.UserRepository;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import com.roble.springproject.RobleElectronic.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class SecurityControllerTest {

    @Mock
    UserDetailsServiceImple userDetailsServiceImple;

    @Mock
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    SecurityController securityController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(securityController).build();
    }

    @Test
    public void login() throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/login"));
    }

    @Test
    public void registrationForm() throws Exception {

        mockMvc.perform(get("/registering"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/registrationForm"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void submitRegistration() throws Exception {

        when(userService.register(any())).thenReturn(new User());

        mockMvc.perform(post("/registering")
                .param("id", "1")
                .param("firstName", "Liban")
                .param("lastName", "Abdullahi")
                .param("userName", "liban")
                .param("email", "libanr4243@gmail.com")
                .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/register_success"));

        verify(userService).register(any());
    }

    @Test
    public void submitRegistrationFailedValidation() throws Exception {

        mockMvc.perform(post("/registering"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/registrationForm"));

    }
}