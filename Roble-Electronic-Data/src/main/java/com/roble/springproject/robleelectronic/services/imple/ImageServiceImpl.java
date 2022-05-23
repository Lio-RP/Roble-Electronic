package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.repositories.ProductRepository;
import com.roble.springproject.RobleElectronic.services.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final ProductRepository productRepository;

    public ImageServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void saveImageFile(Long productId, MultipartFile file) {

        try {
            Product product = productRepository.findById(productId).get();

            Byte[] byteObject = new Byte[file.getBytes().length];
            int i = 0;
            for(byte byt : file.getBytes()){
                byteObject[i++] = byt;
            }

            product.setImage(byteObject);

            productRepository.save(product);

        }catch (IOException e){
            System.out.println("image received" + e);

            e.printStackTrace();
        }

    }
}
