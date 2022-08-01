package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.enums.OrderStatus;
import com.roble.springproject.RobleElectronic.models.Order;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.models.ShoppingCart;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.OrderRepository;
import com.roble.springproject.RobleElectronic.repositories.ShoppingCartRepository;
import com.roble.springproject.RobleElectronic.services.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    public final OrderRepository orderRepository;
    public final ShoppingCartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ShoppingCartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Order save(User user) {

        Order order = new Order();
        List<ShoppingCart> listCarts = cartRepository.findByUser(user);
        List<Product> products = new ArrayList<>();
        float totalPrice = 0;
        int quantity = 0;
        for (ShoppingCart listCart : listCarts) {
            products.add(listCart.getProduct());
            totalPrice += listCart.getSubtotalPrice();
            quantity += listCart.getQuantity();
        }

        Random random = new Random();
        String orderNumber = "";
        for(int i = 1; i < 10; i++){
            orderNumber = orderNumber + random.nextInt(10 - 1) + 1;
        }
        order.setOrderNumber(orderNumber);
        order.setCustomer(user.getCustomer());
        order.setDateOrdered(LocalDate.now());
        order.setStatus(OrderStatus.NEW);
        order.setProducts(products);
        order.setProductQuantity(quantity);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);

    }
}
