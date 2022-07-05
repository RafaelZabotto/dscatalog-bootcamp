package com.rafaelzabotto.dscatalog.services;

import com.rafaelzabotto.dscatalog.dto.CategoryDTO;
import com.rafaelzabotto.dscatalog.dto.ProductDTO;
import com.rafaelzabotto.dscatalog.entities.Category;
import com.rafaelzabotto.dscatalog.entities.Product;
import com.rafaelzabotto.dscatalog.repositories.CategoryRepository;
import com.rafaelzabotto.dscatalog.repositories.ProductRepository;
import com.rafaelzabotto.dscatalog.services.exceptions.DatabaseException;
import com.rafaelzabotto.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Long categoryId, String name, PageRequest pageRequest) {

        //Expressão condicional ternária para evitar a resposta de categoria = 0 e dar erro
        List<Category> categories = (categoryId == 0) ? null : Arrays.asList(categoryRepository.getById(categoryId));

        //Método find customizado para busca com parametros de categoria e nome
        Page<Product> page = productRepository.find(categories, name, pageRequest);
        productRepository.findProductsWithCategories(page.getContent());
        return page.map(x -> new ProductDTO(x, x.getCategories()));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = productRepository.getById(id);
            copyDtoToEntity(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(("Id not Found " + id));

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");

        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        //Limpar as categorias que podem estar na entity
        entity.getCategories().clear();

        //Se vier categorias dentro do ProdutoDTO
        for (CategoryDTO categoryDTO : dto.getCategories()) {
            Category category = categoryRepository.getById(categoryDTO.getId());
            entity.getCategories().add(category);
        }
    }
}
