package com.roble.springproject.robleelectronic.services.imple;

import com.roble.springproject.robleelectronic.models.Product;
import com.roble.springproject.robleelectronic.models.ShoppingCart;
import com.roble.springproject.robleelectronic.repositories.ProductRepository;
import com.roble.springproject.robleelectronic.repositories.ShoppingCartRepository;
import com.roble.springproject.robleelectronic.services.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
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
    public ShoppingCart addToCart(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        List<ShoppingCart> shoppingCartSet = new ArrayList<>();
        cartRepository.findAll().forEach(shoppingCartSet::add);

//        ShoppingCart foundedcart = shoppingCartSet.stream()
//                .filter(cart -> cart.getProduct().getId().equals(productId))
//                .findFirst().orElse(null);

        ShoppingCart newCart = new ShoppingCart();

        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();

            Optional<ShoppingCart> optionalCart = shoppingCartSet.stream()
                    .filter(cart -> cart.getProduct().equals(product))
                    .findFirst();

            if(optionalCart.isPresent()){

                ShoppingCart cart = optionalCart.get();

                newCart.setQuantity(cart.getQuantity() + 1);
                newCart.setSubtotal(cart.getSubtotal() + product.getPrice());
                newCart.setProduct(product);
            }else{
                newCart.setQuantity(1);
                newCart.setSubtotal(product.getPrice());
                newCart.setProduct(product);
            }
        }else{
            throw new RuntimeException(String.format("The product with id %s ", productId + " Not Found"));
        }
        ShoppingCart savedCart = cartRepository.save(newCart);
        return savedCart;
    }
}
