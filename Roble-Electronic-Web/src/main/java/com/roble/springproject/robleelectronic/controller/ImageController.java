package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import com.roble.springproject.RobleElectronic.services.ImageService;
import com.roble.springproject.RobleElectronic.services.ProductService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final ProductService productService;
    public final CategoryService categoryService;

    public ImageController(ImageService imageService,
                           ProductService productService, CategoryService categoryService) {
        this.imageService = imageService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("product")
    public Product productObject(){
        return new Product();
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/roble_elco/admin/product/{productId}/image")
    public String imageForm(@PathVariable("productId") Long productId, Model model){
        model.addAttribute("product", productService.getProductById(productId));
        return "admin/imageUploadForm";
    }

    @PostMapping("/roble_elco/admin/product/{productId}/image")
    public String uploadImage(@PathVariable("productId") Long productId,
                              @RequestParam("image") MultipartFile file){
        imageService.saveImageFile(productId, file);
        return "redirect:/roble_elco/admin/products/" + productId + "/view";
    }

    @GetMapping("/roble_elco/admin/product/{productId}/productImage")
    public void renderImageFromDB(@PathVariable("productId") Long productId,
                                  HttpServletResponse response) throws IOException {

        Product product = productService.getProductById(productId);

        if(product.getImage() != null){
            byte[] byteAray = new byte[product.getImage().length];
            int i = 0;
            for(Byte byt : product.getImage()){
                byteAray[i++] = byt;
            }

            response.setContentType("image/jpg");
            InputStream is = new ByteArrayInputStream(byteAray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }


}
