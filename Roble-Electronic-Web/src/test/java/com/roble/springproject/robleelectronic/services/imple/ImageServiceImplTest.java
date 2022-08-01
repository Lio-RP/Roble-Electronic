package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    ProductRepository productRepository;

    ImageServiceImpl imageServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageServiceImpl = new ImageServiceImpl(productRepository);
    }

    @Test
    public void saveImageFile() throws IOException {

        //Given
        MultipartFile multipartFile = new MockMultipartFile("Imagefile", "testin.txt", "text/plain",
                "Spring framework project Online shopping".getBytes());

        Product product = new Product();
        product.setId(1L);
        product.setName("Iphone 11 pro");

        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        //when
        imageServiceImpl.saveImageFile(product.getId(), multipartFile);

        //then
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());
        Product argumentValue = productArgumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, argumentValue.getImage().length);
    }
}