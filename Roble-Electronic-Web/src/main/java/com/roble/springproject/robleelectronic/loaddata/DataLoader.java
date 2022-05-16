package com.roble.springproject.robleelectronic.loaddata;

import com.roble.springproject.robleelectronic.models.Category;
import com.roble.springproject.robleelectronic.models.Product;
import com.roble.springproject.robleelectronic.services.CategoryService;
import com.roble.springproject.robleelectronic.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryService categoryService;
    private final ProductService productService;

    public DataLoader(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        //Category1
        Category cat1 = new Category();
        cat1.setDescription("Smart Phones");

        //Category2
        Category cat2 = new Category();
        cat2.setDescription("Smart Watches and Fitness bracelets");

        System.out.println("Categories Loaded Successfully");

        //Product1
        Product prod1 = new Product();
        prod1.setName("Fitness bracelets");
        prod1.setVendor("Xiaomi");
        prod1.setPrice(3299);
        prod1.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");

        //Product1
        Product prod2 = new Product();
        prod2.setName("Fitness bracelets two");
        prod2.setVendor("Xiaomi");
        prod2.setPrice(3299);
        prod2.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");

        System.out.println("Products Loaded Successfully");

        //Set references
        cat2.getProducts().add(prod1);
        cat2.getProducts().add(prod2);
        prod1.setCategory(cat2);
        prod2.setCategory(cat2);

        //persist data
        categoryService.save(cat2);
        categoryService.save(cat1);

    }
}
