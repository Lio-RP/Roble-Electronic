package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.repositories.CategoryRepository;
import com.roble.springproject.RobleElectronic.repositories.ProductRepository;
import com.roble.springproject.RobleElectronic.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product save(Product productObject, Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(!categoryOptional.isPresent()){
            throw new RuntimeException(String.format("Category with id ", categoryId + " Not Found"));
        }else{
            Category category = categoryOptional.get();

            Optional<Product> detachedProduct = category.getProducts().stream()
                    .filter(prod -> prod.getId().equals(productObject.getId()))
                    .findFirst();

            //Product savedProduct = new Product();
            Category savedCategory = new Category();

            if(detachedProduct.isPresent()){
                productRepository.save(productObject);
                savedCategory = categoryRepository.save(category);
            }else{

                productObject.setCategory(category);
                category.getProducts().add(productObject);
                savedCategory = categoryRepository.save(category);
                //savedProduct = productRepository.save(product);
            }
            Optional<Product> savedProduct = savedCategory.getProducts().stream()
                    .filter(product -> product.getId().equals(productObject.getId()))
                    .findFirst();

            if(!savedProduct.isPresent()){
                savedProduct = savedCategory.getProducts().stream()
                        .filter(product -> product.getName().equals(productObject.getName()))
                        .filter(product -> product.getBrand().equals(productObject.getBrand()))
                        .filter(product -> product.getDescription().equals(productObject.getDescription()))
                        .findFirst();
            }
            return savedProduct.get();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public List<Product> getProductsBasedOnCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(!categoryOptional.isPresent()){
            throw new RuntimeException(String.format("Category with id %s ", id + " Not Found"));
        }

        Category category = categoryOptional.get();

        List<Product> products = category.getProducts();

        return products;
    }

    @Override
    public Product getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElse(null);

        return product;
    }

    @Override
    public List<Product> findByNameLike(String name) {
        return productRepository.findByNameLike(name);
    }

    @Override
    public List<Product> findByNameLike(String name, String description) {
        return productRepository.findByNameLike(name, description);
    }

    @Override
    public void deleteById(Long productId, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        Product product = productRepository.findById(productId).get();

        category.getProducts().remove(product);
        product.setCategory(null);
        categoryRepository.save(category);
       // productRepository.delete(product);
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }
}
