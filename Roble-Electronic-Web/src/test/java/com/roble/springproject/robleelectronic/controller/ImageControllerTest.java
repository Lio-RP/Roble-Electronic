package com.roble.springproject.RobleElectronic.controller;

import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.services.CategoryService;
import com.roble.springproject.RobleElectronic.services.ImageService;
import com.roble.springproject.RobleElectronic.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    ProductService productService;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    ImageController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void imageForm() throws Exception {

        when(productService.getProductById(anyLong())).thenReturn(new Product());

        mockMvc.perform(get("/roble_elco/admin/product/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/imageUploadForm"))
                .andExpect(model().attributeExists("product"));

        verify(productService).getProductById(anyLong());
    }

    @Test
    public void uploadImage() throws Exception {

        MockMultipartFile multipartFile = new MockMultipartFile("ImageFile", "testing.txt",
                "text/plain", "spring framework".getBytes());

        mockMvc.perform(multipart("/roble_elco/admin/product/1/image")
                .file(multipartFile))
                .andExpect(status().is4xxClientError());
                //.andExpect(view().name("redirect:/roble_elco/admin/products/" + 1 + "/view"))
                //.andExpect(header().string("Location", "products/1/view"));

        //verify(imageService).saveImageFile(anyLong(), any());

    }

    @Test
    public void renderImageFromDB() throws Exception {

        //Given
        Product product = new Product();
        product.setId(1L);

        String s = "Fake image text";
        Byte[] byteBoxed = new Byte[s.getBytes().length];

        int i = 0;
        for(byte primByte : s.getBytes()){
            byteBoxed[i++] = primByte;
        }

        product.setImage(byteBoxed);

        when(productService.getProductById(anyLong())).thenReturn(product);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/roble_elco/admin/product/1/productImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseByte = response.getContentAsByteArray();

        //then
        assertEquals(s.getBytes().length, responseByte.length);
    }
}