package com.andradscorporation.dscatalog.services;

import com.andradscorporation.dscatalog.dto.CategoryDTO;
import com.andradscorporation.dscatalog.dto.ProductDTO;
import com.andradscorporation.dscatalog.entities.Category;
import com.andradscorporation.dscatalog.entities.Product;
import com.andradscorporation.dscatalog.repositories.CategoryRepository;
import com.andradscorporation.dscatalog.repositories.ProductRepository;
import com.andradscorporation.dscatalog.services.exceptions.DatabaseException;
import com.andradscorporation.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable){
        Page<Product> list = repository.findAll(pageable);
        return list.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Optional<Product> obj = repository.findById(id);
        Product categoryEntity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(categoryEntity, categoryEntity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO categoryDTO) {
        Product categoryEntity = new Product();
        copyDtoToEntity(categoryDTO, categoryEntity);
        categoryEntity = repository.save(categoryEntity);
        return new ProductDTO();
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO categoryDTO) {
        try {
            Product categoryEntity = repository.getOne(id);
            copyDtoToEntity(categoryDTO, categoryEntity);
            categoryEntity = repository.save(categoryEntity);
            return new ProductDTO(categoryEntity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }

    }

    private void copyDtoToEntity(ProductDTO categoryDTO, Product categoryEntity) {

        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setDescription(categoryDTO.getDescription());
        categoryEntity.setDate(categoryDTO.getDate());
        categoryEntity.setPrice(categoryDTO.getPrice());
        categoryEntity.setImgUrl(categoryDTO.getImgUrl());

        categoryEntity.getCategories().clear();
        for(CategoryDTO catDTO : categoryDTO.getCategories()){
            Category category = categoryRepository.getOne(catDTO.getId());
            categoryEntity.getCategories().add(category);
        }

    }

}
