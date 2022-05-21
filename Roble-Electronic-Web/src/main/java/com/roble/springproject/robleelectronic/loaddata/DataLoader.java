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

        Category smart_phones = new Category();
        smart_phones.setDescription("Smart Phones");

        Category smart_watches = new Category();
        smart_watches.setDescription("Smart Watches and Fitness bracelets");

        Category tablets = new Category();
        tablets.setDescription("Tablets");

        Category notebooks = new Category();
        notebooks.setDescription("Notebooks");

        Category headphones = new Category();
        headphones.setDescription("HeadPhones");

        Category accessories = new Category();
        accessories.setDescription("Accessories For Mobiles");

        Category cameras = new Category();
        cameras.setDescription("Cameras");

        Category games = new Category();
        games.setDescription("Play-stations");

        System.out.println("Categories Loaded Successfully");

        Product bracelets= new Product();
        bracelets.setName("Fitness bracelets");
        bracelets.setVendor("Xiaomi");
        bracelets.setPrice(3299);
        bracelets.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");

        Product bracelets2 = new Product();
        bracelets2.setName("Fitness bracelets");
        bracelets2.setVendor("Xiaomi");
        bracelets2.setPrice(3299);
        bracelets2.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");

        Product iphone1 = new Product();
        iphone1.setName("Iphone Red 128GB");
        iphone1.setVendor("");
        iphone1.setPrice(159);
        iphone1.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");

        Product googlePixel = new Product();
        googlePixel.setName("Google Pixel Blue");
        googlePixel.setVendor("");
        googlePixel.setPrice(256);
        googlePixel.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");

        Product microsoftXbox = new Product();
        microsoftXbox.setName("Microsoft Xbox One s");
        microsoftXbox.setPrice(545);
        microsoftXbox.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");

        System.out.println("Products Loaded Successfully");

        //Give Categories their products:
        smart_watches.getProducts().add(bracelets);
        smart_watches.getProducts().add(bracelets2);
        smart_phones.getProducts().add(iphone1);
        smart_phones.getProducts().add(googlePixel);
        games.getProducts().add(microsoftXbox);


        //give products their categories
        bracelets.setCategory(smart_watches);
        bracelets2.setCategory(smart_watches);
        iphone1.setCategory(smart_phones);
        googlePixel.setCategory(smart_phones);
        microsoftXbox.setCategory(games);

        //persist data
        categoryService.save(smart_watches);
        categoryService.save(smart_phones);
        categoryService.save(games);
        categoryService.save(tablets);
        categoryService.save(notebooks);
        categoryService.save(headphones);
        categoryService.save(accessories);
        categoryService.save(cameras);

    }
}
