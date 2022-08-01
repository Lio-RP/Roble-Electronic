package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Order;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.models.ShoppingCart;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.OrderRepository;
import com.roble.springproject.RobleElectronic.repositories.ShoppingCartRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ShoppingCartRepository cartRepository;

    OrderServiceImpl orderServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        orderServiceImpl = new OrderServiceImpl(orderRepository, cartRepository);
    }

    @Test
    public void save() {

        User user = new User();
        user.setId(1L);

        Product prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("Product1 name");
        prod1.setPrice(234);

        Product prod2 = new Product();
        prod2.setId(2L);
        prod2.setName("Product1 name");
        prod2.setPrice(567);

        List<Product> listProducts = Arrays.asList(prod1, prod2);

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setQuantity(2);
        cart1.setSubtotalPrice(prod1.getPrice() + prod2.getPrice());

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setQuantity(2);
        cart2.setSubtotalPrice(prod1.getPrice() + prod2.getPrice());


        List<ShoppingCart> listcarts = new ArrayList<>();
        listcarts.add(cart1);
        listcarts.add(cart2);

        Order order = new Order();
        order.setId(1L);
        order.setOrderNumber("123456789");
        order.setProducts(listProducts);
        order.setProductQuantity(cart1.getQuantity() + cart2.getQuantity());
        order.setTotalPrice(cart1.getSubtotalPrice() + cart2.getSubtotalPrice());

        when(cartRepository.findByUser(any(User.class))).thenReturn(listcarts);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //when
        Order savedOrder = orderServiceImpl.save(user);

        //then
        assertNotNull(savedOrder);
        assertEquals(order.getId(), savedOrder.getId());
        assertEquals(2, savedOrder.getProducts().size());

    }
}