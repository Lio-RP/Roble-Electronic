package com.roble.springproject.RobleElectronic.services.imple;

import com.roble.springproject.RobleElectronic.models.Category;
import com.roble.springproject.RobleElectronic.models.Product;
import com.roble.springproject.RobleElectronic.repositories.CategoryRepository;
import com.roble.springproject.RobleElectronic.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    ProductServiceImpl productServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        productServiceImpl = new ProductServiceImpl(productRepository, categoryRepository);
    }

    @Test
    public void saveExistingProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Product Name");

        Category category = new Category();
        category.setId(1L);
        category.setDescription("Category Description");
        category.getProducts().add(product);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        //when
        Product savedProduct = productServiceImpl.save(product, category.getId());

        assertNotNull(savedProduct);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryRepository, times(1)).findById(anyLong());
        assertEquals(product.getId(), savedProduct.getId());

    }

    @Test
    public void saveNewProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Product Name");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product Name");

        Category category = new Category();
        category.setId(1L);
        category.setDescription("Category Description");
        category.getProducts().add(product2);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        //when
        Product savedProduct = productServiceImpl.save(product, category.getId());

        assertNotNull(savedProduct);
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryRepository, times(1)).findById(anyLong());
        //verify(productRepository).save(any(Product.class));

    }

    @Test
    public void getAllProducts() {

        List<Product> productList = Arrays.asList(new Product(), new Product());

        when(productRepository.findAll()).thenReturn(productList);

        //when
        List<Product> products = productServiceImpl.getAllProducts();

        //then
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();

    }

    @Test
    public void getProductsBasedOnCategory() {
        Product prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("Product1 name");

        Product prod2 = new Product();
        prod2.setId(2L);
        prod2.setName("Product2 name");

        Category category = new Category();
        category.setId(1L);
        category.setDescription("Category Description");

        category.getProducts().add(prod1);
        category.getProducts().add(prod2);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        //when
        List<Product> products = productServiceImpl.getProductsBasedOnCategory(category.getId());

        //then
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getProductById() {

        Product prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("Product1 name");

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(prod1));

        //when
        Product foundProduct = productServiceImpl.getProductById(1L);

        //then
        assertNotNull(foundProduct);
        assertEquals(Long.valueOf(1L), foundProduct.getId());
    }

    @Test
    public void findByNameLike() {

        List<Product> productList = Arrays.asList(new Product(), new Product());

        when(productRepository.findByNameLike(anyString())).thenReturn(productList);

        //when
        List<Product> products = productServiceImpl.findByNameLike("product name");

        //then
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByNameLike(anyString());
    }

    @Test
    public void testFindByNameLike() {
        List<Product> productList = Arrays.asList(new Product(), new Product());

        when(productRepository.findByNameLike(anyString(), anyString())).thenReturn(productList);

        //when
        List<Product> products = productServiceImpl.findByNameLike("product name", "Category Description");

        //then
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByNameLike(anyString(), anyString());
    }

    @Test
    public void deleteAll() {

        productServiceImpl.deleteAll();

        verify(productRepository, times(1)).deleteAll();
    }

    @Test
    public void deleteById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product name");

        Category category = new Category();
        category.setId(1L);
        category.setDescription("Category Description.");
        category.getProducts().add(product);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(category);

        assertEquals(1, category.getProducts().size());

        productServiceImpl.deleteById(product.getId(), category.getId());

        assertEquals(0, category.getProducts().size());
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(categoryRepository, times(1)).save(any());
        verify(productRepository, times(1)).findById(anyLong());
    }
}