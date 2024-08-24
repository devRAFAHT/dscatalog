package com.andradscorporation.dscatalog.services;

import com.andradscorporation.dscatalog.dto.ProductDTO;
import com.andradscorporation.dscatalog.entities.Category;
import com.andradscorporation.dscatalog.entities.Product;
import com.andradscorporation.dscatalog.repositories.ProductRepository;
import com.andradscorporation.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    ProductRepository productRepository;

    private Long countTotalProducts;

    @BeforeEach
    void setUpMocks() throws Exception{
        MockitoAnnotations.openMocks(this);
        countTotalProducts = 25L;
    }

    @Test
    void findByIdShouldReturnNotNullWhenIdIsValid(){
        Product product = Factory.createProduct();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        var result = productService.findById(1L);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getCategories());
        Assertions.assertNotNull(result.getName());
        Assertions.assertNotNull(result.getDate());
        Assertions.assertNotNull(result.getCategories());
        Assertions.assertNotNull(result.getDescription());
        Assertions.assertNotNull(result.getPrice());
        Assertions.assertNotNull(result.getImgUrl());
        Assertions.assertTrue(result.toString().contains("links: [</products/1>;rel=\"self\"]"));
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        Product product = Factory.createProduct();
        product.setId(null);

        Product persisted = product;

        ProductDTO productDTO = Factory.createProductDTO();

        when(productRepository.save(product)).thenReturn(persisted);

        var result = productService.insert(productDTO);

        Assertions.assertTrue(result.toString().contains("links: [</products/1>;rel=\"self\"]"));
    }

}
