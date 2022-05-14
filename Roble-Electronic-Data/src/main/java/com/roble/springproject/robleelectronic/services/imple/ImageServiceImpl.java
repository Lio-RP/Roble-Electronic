package com.roble.springproject.robleelectronic.services.imple;

import com.roble.springproject.robleelectronic.models.Product;
import com.roble.springproject.robleelectronic.repositories.ProductRepository;
import com.roble.springproject.robleelectronic.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
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
            log.debug("image received", e);

            e.printStackTrace();
        }

    }
}
