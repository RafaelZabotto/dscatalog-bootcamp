package com.rafaelzabotto.dscatalog.services;

import static org.mockito.ArgumentMatchers.any;

import com.rafaelzabotto.dscatalog.dto.ProductDTO;
import com.rafaelzabotto.dscatalog.entities.Category;
import com.rafaelzabotto.dscatalog.entities.Product;
import com.rafaelzabotto.dscatalog.repositories.CategoryRepository;
import com.rafaelzabotto.dscatalog.repositories.ProductRepository;
import com.rafaelzabotto.dscatalog.services.exceptions.DatabaseException;
import com.rafaelzabotto.dscatalog.services.exceptions.ResourceNotFoundException;
import com.rafaelzabotto.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private Product product;
    private ProductDTO productDTO;
    private Category category;

    // PageImpl tipo concreto que representa uma página de dados, em testes usamos ele
    private PageImpl<Product> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 4L;
        product = Factory.createProduct();
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(product));
        category = Factory.createCategory();

        // Configurando comportamentos que não retornam void
        Mockito.when(productRepository.findAll((Pageable) any())).thenReturn(page);

        Mockito.when(productRepository.save(any())).thenReturn(product);

        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(productRepository.find(any(),any(),any())).thenReturn(page);

        Mockito.when(productRepository.getById(existingId)).thenReturn(product);
        Mockito.when(productRepository.getById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(categoryRepository.getById(existingId)).thenReturn(category);
        Mockito.when(categoryRepository.getById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        //Configurando um comportamento simulado que retornam void com o mockito
        Mockito.doNothing().when(productRepository).deleteById(existingId);

        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenNonExistingId() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.update(nonExistingId, productDTO);
        });
        Mockito.verify(productRepository, Mockito.times(1)).getById(nonExistingId);

    }

    @Test
    public void updateShouldReturnProductDTOWhenExistingId() {

        ProductDTO result = productService.update(existingId, productDTO);

        Assertions.assertNotNull(result);
        Mockito.verify(productRepository, Mockito.times(1)).getById(existingId);

    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenNonExistingId() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(nonExistingId);
        });
        Mockito.verify(productRepository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenExistingId() {

        ProductDTO result = productService.findById(existingId);

        Assertions.assertNotNull(result);
        Mockito.verify(productRepository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findAllPagedShouldReturnPage() {

        PageRequest pageable = PageRequest.of(0,10);

        Page<ProductDTO> result = productService.findAllPaged(0L, "", pageable);

        Assertions.assertNotNull(result);

    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(DatabaseException.class, () -> {
            productService.delete(dependentId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(nonExistingId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        Assertions.assertDoesNotThrow(() -> {
            productService.delete(existingId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
    }

}
