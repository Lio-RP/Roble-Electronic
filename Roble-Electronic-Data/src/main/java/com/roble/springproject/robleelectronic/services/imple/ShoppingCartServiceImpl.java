package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.models.ShoppingCart;
import com.roble.springproject.RobleElectronic.models.User;
import com.roble.springproject.RobleElectronic.repositories.ProductRepository;
import com.roble.springproject.RobleElectronic.repositories.ShoppingCartRepository;
import com.roble.springproject.RobleElectronic.services.ShoppingCartService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductRepository productRepository;
    private final ShoppingCartRepository cartRepository;

    public ShoppingCartServiceImpl(ProductRepository productRepository,
                                   ShoppingCartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public ShoppingCart addToCart(Long productId, User user) {

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(!optionalProduct.isPresent()){
            throw new RuntimeException("Product with id " + productId + " Not Found");
        }

        Product product = optionalProduct.get();

        List<ShoppingCart> listCarts = listCartsByUser(user);

        Optional<ShoppingCart> cartOptional = listCarts
                 .stream()
                 .filter(cart -> cart.getProduct().getId().equals(productId))
                 .findFirst();
        if(!cartOptional.isPresent()){

            ShoppingCart newCartItem = new ShoppingCart();

            newCartItem.setUser(user);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(1);
            newCartItem.setSubtotalPrice(product.getPrice());

            ShoppingCart savedCart = cartRepository.save(newCartItem);

            return savedCart;
        }

        ShoppingCart cart = cartOptional.get();
        updateCartItem(cart.getId(), user);

        return cart;
    }

    @Override
    public List<ShoppingCart> listCartsByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public float calTotalCartsPrice(List<ShoppingCart> listCarts) {

        float totalCartsPrice = 0;
        for(int i = 0; i < listCarts.size(); i++){
            totalCartsPrice += listCarts.get(i).getSubtotalPrice();
        }
        return totalCartsPrice;
    }

    @Override
    public int calTotalCartsQuantity(List<ShoppingCart> listCarts) {

        int totalcartsQuantity = 0;
        for(int i = 0; i < listCarts.size(); i++){

            totalcartsQuantity += listCarts.get(i).getQuantity();
        }
        return totalcartsQuantity;
    }

    @Override
    public void deleteCartItem(Long cartId, User user) {

        List<ShoppingCart> listCarts = listCartsByUser(user);

        Optional<ShoppingCart> cartOptional = listCarts.stream().filter(cart -> cart.getId().equals(cartId))
                .findFirst();

        if(cartOptional.isPresent()){
            ShoppingCart cart = cartOptional.get();
            cartRepository.delete(cart);
        }else {
            throw new RuntimeException("The cart with id " + cartId + " Not Found!");
        }
    }

    @Override
    public void clearCart(User user) {
        List<ShoppingCart> listCarts = listCartsByUser(user);
        cartRepository.deleteAll(listCarts);
    }

    @Override
    public void updateCartItem(Long cartId, User user) {
        List<ShoppingCart> listCarts = listCartsByUser(user);

        Optional<ShoppingCart> cartOptional = listCarts.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst();

        if(cartOptional.isPresent()){
            ShoppingCart cart = cartOptional.get();

            cart.setQuantity(cart.getQuantity() + 1);
            cart.setSubtotalPrice(cart.getProduct().getPrice() * cart.getQuantity());

            cartRepository.save(cart);
        }
    }

    @Override
    public void decreaseCartItem(Long cartId, User user) {

        List<ShoppingCart> listCarts = listCartsByUser(user);

        Optional<ShoppingCart> cartOptional = listCarts.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst();

        if(cartOptional.isPresent()){
            ShoppingCart cart = cartOptional.get();

            if(cart.getQuantity() > 1){
                cart.setQuantity(cart.getQuantity() - 1);
                cart.setSubtotalPrice(cart.getProduct().getPrice() * cart.getQuantity());

                cartRepository.save(cart);
            }else {
                cartRepository.delete(cart);
            }
        }
    }
}
