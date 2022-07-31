package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.models.ShoppingCart;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import com.roble.springproject.RobleElectronic.services.ProductService;
import com.roble.springproject.RobleElectronic.services.ShoppingCartService;
import com.roble.springproject.RobleElectronic.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ShoppingCartControllerTest {

    @Mock
    ProductService productService;

    @Mock
    ShoppingCartService shoppingCartService;

    @Mock
    UserService userService;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    ShoppingCartController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void addToCart() throws Exception {

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(new User());
        when(shoppingCartService.addToCart(anyLong(), any())).thenReturn(new ShoppingCart());

        mockMvc.perform(get("/roble_elco/products/1/addToCart"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roble_elco/cart"));

        verify(userService).getCurrentlyLoggedUser(any());
        verify(shoppingCartService).addToCart(anyLong(), any());
    }

    @Test
    public void displayCart() throws Exception {
        List<ShoppingCart> listCarts = Arrays.asList(new ShoppingCart(), new ShoppingCart());

        when(shoppingCartService.listCartsByUser(any())).thenReturn(listCarts);
        when(userService.getCurrentlyLoggedUser(any())).thenReturn(new User());

        mockMvc.perform(get("/roble_elco/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/shoppingCart"))
                .andExpect(model().attributeExists("totalCartsPrice"))
                .andExpect(model().attributeExists("totalCartsQuantity"))
                .andExpect(model().attribute("listCarts", hasSize(2)));

        verify(userService).getCurrentlyLoggedUser(any());
        verify(shoppingCartService).listCartsByUser(any());
    }

    @Test
    public void deleteCartItem() throws Exception {

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(new User());

        mockMvc.perform(get("/roble_elco/cart/1/deleteItem"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roble_elco/cart"));

        verify(userService).getCurrentlyLoggedUser(any());
        verify(shoppingCartService).deleteCartItem(anyLong(), any());
    }

    @Test
    public void updateCartItem() throws Exception {

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(new User());

        mockMvc.perform(get("/roble_elco/cart/1/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roble_elco/cart"));

        verify(userService).getCurrentlyLoggedUser(any());
        verify(shoppingCartService).updateCartItem(anyLong(), any());
    }

    @Test
    public void decreaseCartItem() throws Exception {

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(new User());

        mockMvc.perform(get("/roble_elco/cart/1/decrease"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roble_elco/cart"));

        verify(userService).getCurrentlyLoggedUser(any());
        verify(shoppingCartService).decreaseCartItem(anyLong(), any());
    }
}