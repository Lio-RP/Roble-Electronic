package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.auth.UserPrinciples;
import com.roble.springproject.RobleElectronic.models.Order;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.models.ShoppingCart;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.services.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest {


    @Mock
    ProductService productService;

    @Mock
    ShoppingCartService shoppingCartService;

    @Mock
    CategoryService categoryService;

    @Mock
    UserService userService;

    @Mock
    CustomerService customerService;

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderController orderContrtoller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(orderContrtoller).build();
    }

    @Test
    public void getListCarts() {

        User user = new User();
        user.setId(1L);
        user.setFirstName("Liban");
        user.setLastName("Abdullahi");
        user.setEmail("libanr4243@gmail.com");
        user.setPassword("password123");

        List<ShoppingCart> carts = Arrays.asList(new ShoppingCart(), new ShoppingCart());

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(user);
        when(shoppingCartService.listCartsByUser(any(User.class))).thenReturn(carts);

        //when
        List<ShoppingCart> listCarts = orderContrtoller.getListCarts(new UserPrinciples());

        assertNotNull(listCarts);
        assertEquals(2, listCarts.size());
        verify(userService).getCurrentlyLoggedUser(any());

    }

    @Test
    public void getTotalCartsPrice() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product1");
        product1.setPrice(123);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product1");
        product2.setPrice(456);

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setSubtotalPrice(product1.getPrice() + product2.getPrice());

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setSubtotalPrice(product1.getPrice() + product2.getPrice());

        List<ShoppingCart> listCarts = Arrays.asList(cart1, cart2);

        float totalPrice = cart1.getSubtotalPrice() + cart2.getSubtotalPrice();

        when(shoppingCartService.calTotalCartsPrice(any())).thenReturn(totalPrice);

        //when
        float totalCartsPrice = orderContrtoller.getTotalCartsPrice(listCarts);

        assertThat(totalPrice, equalTo(totalCartsPrice));
        verify(shoppingCartService).calTotalCartsPrice(any());
    }

    @Test
    public void getTotalCartsQuantity() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product1");
        product1.setPrice(123);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product1");
        product2.setPrice(456);

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setSubtotalPrice(product1.getPrice() + product2.getPrice());
        cart1.setQuantity(2);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setSubtotalPrice(product1.getPrice() + product2.getPrice());
        cart2.setQuantity(2);

        List<ShoppingCart> listCarts = Arrays.asList(cart1, cart2);

        int totalQuantity = cart1.getQuantity() + cart2.getQuantity();

        when(shoppingCartService.calTotalCartsQuantity(any())).thenReturn(totalQuantity);

        //when
        int totalCartsQuantity = orderContrtoller.getTotalCartsQuantity(listCarts);

        assertEquals(totalQuantity, totalCartsQuantity);
        verify(shoppingCartService).calTotalCartsQuantity(any());
    }

    @Test
    public void initCheckoutForm() throws Exception {

        List<ShoppingCart> carts = Arrays.asList(new ShoppingCart(), new ShoppingCart());
        float totalPrice = 123;

        when(shoppingCartService.listCartsByUser(any(User.class))).thenReturn(carts);
        when(shoppingCartService.calTotalCartsPrice(any())).thenReturn(totalPrice);
        when(shoppingCartService.calTotalCartsQuantity(any())).thenReturn(123);

        mockMvc.perform(get("/roble_elco/checkout"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/checkout"))
                .andExpect(model().attributeExists("totalCartsPrice"))
                .andExpect(model().attributeExists("totalCartsQuantity"))
                //.andExpect(model().attribute("listCarts", hasSize(2)))
                .andExpect(model().attributeExists("customer"));

        verify(shoppingCartService, times(3)).listCartsByUser(any());
        verify(shoppingCartService).calTotalCartsPrice(any());
        verify(shoppingCartService).calTotalCartsQuantity(any());
    }

    @Test
    public void processCheckoutForm() throws Exception {

        mockMvc.perform(post("/roble_elco/checkout")
                    .param("id", "1")
                    .param("firstName", "Liban")
                    .param("lastName", "Abdullahi")
                    .param("phoneNumber", "+79964426139")
                    .param("email", "libanr4243@gmail.com")
                    .param("address", "stroiteley avenue 7g")
                    .param("city", "Vladimir")
                    .param("country", "Russia")
                    .param("zipcode", "600001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roble_elco/payment"));
    }

    @Test
    public void processCheckoutFormFailValidation() throws Exception {

        mockMvc.perform(post("/roble_elco/checkout"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/checkout"))
                .andExpect(model().attributeExists("totalCartsPrice"))
                .andExpect(model().attributeExists("totalCartsQuantity"))
                .andExpect(model().attribute("listCarts", hasSize(0)))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    public void initPaymentForm() throws Exception {

        List<ShoppingCart> carts = Arrays.asList(new ShoppingCart(), new ShoppingCart());
        float totalPrice = 123;

        when(shoppingCartService.listCartsByUser(any(User.class))).thenReturn(carts);
        when(shoppingCartService.calTotalCartsPrice(any())).thenReturn(totalPrice);
        when(shoppingCartService.calTotalCartsQuantity(any())).thenReturn(123);

        mockMvc.perform(get("/roble_elco/payment"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/payment"))
                .andExpect(model().attributeExists("totalCartsPrice"))
                .andExpect(model().attributeExists("totalCartsQuantity"))
                //.andExpect(model().attribute("listCarts", hasSize(2)))
                .andExpect(model().attributeExists("payment"));

        verify(shoppingCartService, times(3)).listCartsByUser(any());
        verify(shoppingCartService).calTotalCartsPrice(any());
        verify(shoppingCartService).calTotalCartsQuantity(any());
    }

    @Test
    public void processPaymentForm() throws Exception {

        when(orderService.save(any())).thenReturn(new Order());

        mockMvc.perform(post("/roble_elco/payment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roble_elco/receipt"));

        verify(orderService).save(any());
    }

    @Test
    public void receipt() throws Exception {

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(new User());

        mockMvc.perform(get("/roble_elco/receipt"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/receipt"))
                .andExpect(model().attributeExists("totalCartsPrice"))
                .andExpect(model().attributeExists("totalCartsQuantity"))
                .andExpect(model().attribute("listCarts", hasSize(0)));

        verify(userService, times(4)).getCurrentlyLoggedUser(any());
        verify(shoppingCartService).clearCart(any());
    }
}