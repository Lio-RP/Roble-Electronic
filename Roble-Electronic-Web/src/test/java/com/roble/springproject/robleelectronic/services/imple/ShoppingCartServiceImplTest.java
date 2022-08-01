package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.models.ShoppingCart;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.ProductRepository;
import com.roble.springproject.RobleElectronic.repositories.ShoppingCartRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ShoppingCartServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ShoppingCartRepository cartRepository;

    ShoppingCartServiceImpl shoppingCartService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        shoppingCartService = new ShoppingCartServiceImpl(productRepository, cartRepository);
    }

    @Test
    public void addToCart() {

        //Given
        Product prod1 = new Product();
        prod1.setId(2L);

        Product prod2 = new Product();
        prod2.setId(3L);

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setProduct(prod1);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setProduct(prod2);

        List<ShoppingCart> carts = Arrays.asList(cart1, cart2);

        Product productToAddCart = new Product();
        productToAddCart.setId(1L);
        productToAddCart.setName("Iphone");

        ShoppingCart newCartItem = new ShoppingCart();
        newCartItem.setId(1L);
        newCartItem.setUser(new User());
        newCartItem.setProduct(productToAddCart);
        newCartItem.setQuantity(1);
        newCartItem.setSubtotalPrice(productToAddCart.getPrice());

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productToAddCart));
        when(cartRepository.findByUser(any(User.class))).thenReturn(carts);
        when(cartRepository.save(any())).thenReturn(newCartItem);

        //When
        ShoppingCart savedCart = shoppingCartService.addToCart(productToAddCart.getId(), new User());

        //then
        assertNotNull(savedCart);
        assertEquals(productToAddCart.getId(), savedCart.getProduct().getId());
        verify(productRepository, times(1)).findById(anyLong());
        verify(cartRepository, times(1)).findByUser(any(User.class));
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    public void listCartsByUser() {

        //Given
        Product prod1 = new Product();
        prod1.setId(2L);

        Product prod2 = new Product();
        prod2.setId(3L);

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setProduct(prod1);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setProduct(prod2);

        List<ShoppingCart> carts = Arrays.asList(cart1, cart2);

        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        when(cartRepository.findByUser(any(User.class))).thenReturn(carts);

        //when
        List<ShoppingCart> listCarts = shoppingCartService.listCartsByUser(user);

        //then
        verify(cartRepository, times(1)).findByUser(any(User.class));
        assertEquals(2, listCarts.size());
    }

    @Test
    public void calTotalCartsPrice() {

        //Given
        Product prod1 = new Product();
        prod1.setId(2L);
        prod1.setPrice(12345);

        Product prod2 = new Product();
        prod2.setId(3L);
        prod2.setPrice(6789);

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setProduct(prod1);
        cart1.setSubtotalPrice(prod1.getPrice());

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setProduct(prod2);
        cart2.setSubtotalPrice(prod2.getPrice());

        List<ShoppingCart> carts = Arrays.asList(cart1, cart2);

        //when
        float totalCartsPrice = shoppingCartService.calTotalCartsPrice(carts);

        //then
        assertThat(cart1.getSubtotalPrice() + cart2.getSubtotalPrice(), equalTo(totalCartsPrice));
    }

    @Test
    public void calTotalCartsQuantity() {


        //Given
        Product prod1 = new Product();
        prod1.setId(2L);
        prod1.setPrice(12345);

        Product prod2 = new Product();
        prod2.setId(3L);
        prod2.setPrice(6789);

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setProduct(prod1);
        cart1.setSubtotalPrice(prod1.getPrice());
        cart1.setQuantity(1);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setProduct(prod2);
        cart2.setSubtotalPrice(prod2.getPrice());
        cart2.setQuantity(1);

        List<ShoppingCart> carts = Arrays.asList(cart1, cart2);

        //when
        int quantity = shoppingCartService.calTotalCartsQuantity(carts);

        //then
        assertEquals(cart1.getQuantity() + cart2.getQuantity(), quantity);
    }

    @Test
    public void deleteCartItem() {

        //Given
        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setQuantity(1);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setQuantity(1);

        List<ShoppingCart> carts = Arrays.asList(cart1, cart2);

        Long cartIdToDelete = cart1.getId();

        when(cartRepository.findByUser(any(User.class))).thenReturn(carts);

        //when
        shoppingCartService.deleteCartItem(cartIdToDelete, new User());

        //then
        verify(cartRepository, times(1)).delete(any());
        verify(cartRepository, times(1)).findByUser(any());
    }

    @Test
    public void clearCart() {

        //Given
        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setQuantity(1);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setQuantity(1);

        List<ShoppingCart> carts = Arrays.asList(cart1, cart2);

        when(cartRepository.findByUser(any())).thenReturn(carts);

        //when
        shoppingCartService.clearCart(new User());

        //then
        verify(cartRepository, times(1)).deleteAll(carts);
        verify(cartRepository, times(1)).findByUser(any());
    }

    @Test
    public void updateCartItem() {

        //Given
        Product prod1 = new Product();
        prod1.setId(2L);
        prod1.setPrice(12345);

        Product prod2 = new Product();
        prod2.setId(3L);
        prod2.setPrice(6789);

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setProduct(prod1);
        cart1.setSubtotalPrice(prod1.getPrice());
        cart1.setQuantity(1);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setProduct(prod2);
        cart2.setSubtotalPrice(prod2.getPrice());
        cart2.setQuantity(1);

        List<ShoppingCart> carts = Arrays.asList(cart1, cart2);

        Long cartIdToUpdate = cart1.getId();

        when(cartRepository.findByUser(any())).thenReturn(carts);

        //when
        shoppingCartService.updateCartItem(cartIdToUpdate, new User());

        //then
        assertEquals(2, cart1.getQuantity());
        assertThat(2 * prod1.getPrice(), equalTo(cart1.getSubtotalPrice()));
        verify(cartRepository, times(1)).findByUser(any());
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    public void decreaseCartItemTest1() {


        //Given
        Product prod1 = new Product();
        prod1.setId(2L);
        prod1.setPrice(12345);

        Product prod2 = new Product();
        prod2.setId(3L);
        prod2.setPrice(6789);

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setProduct(prod1);
        cart1.setSubtotalPrice(prod1.getPrice());
        cart1.setQuantity(2);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setProduct(prod2);
        cart2.setSubtotalPrice(prod2.getPrice());
        cart2.setQuantity(1);

        List<ShoppingCart> carts = Arrays.asList(cart1, cart2);

        Long cartIdToDecrees = cart1.getId();

        when(cartRepository.findByUser(any())).thenReturn(carts);

        //when
        shoppingCartService.decreaseCartItem(cartIdToDecrees, new User());

        //then
        assertEquals(1, cart1.getQuantity());
        verify(cartRepository, times(1)).findByUser(any());
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    public void decreaseCartItemTest2() {


        //Given
        Product prod1 = new Product();
        prod1.setId(2L);
        prod1.setPrice(12345);

        Product prod2 = new Product();
        prod2.setId(3L);
        prod2.setPrice(6789);

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setProduct(prod1);
        cart1.setSubtotalPrice(prod1.getPrice());
        cart1.setQuantity(1);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setProduct(prod2);
        cart2.setSubtotalPrice(prod2.getPrice());
        cart2.setQuantity(1);

        List<ShoppingCart> carts = Arrays.asList(cart1, cart2);

        Long cartIdToDecrees = cart1.getId();

        when(cartRepository.findByUser(any())).thenReturn(carts);

        //when
        shoppingCartService.decreaseCartItem(cartIdToDecrees, new User());

        //then
        verify(cartRepository, times(1)).findByUser(any());
        verify(cartRepository, times(1)).delete(any());
    }
}