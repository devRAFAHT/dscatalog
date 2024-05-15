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
        Product ProductEntity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(ProductEntity, ProductEntity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO ProductDTO) {
        Product ProductEntity = new Product();
        copyDtoToEntity(ProductDTO, ProductEntity);
        ProductEntity = repository.save(ProductEntity);
        return new ProductDTO();
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO ProductDTO) {
        try {
            Product ProductEntity = repository.getOne(id);
            copyDtoToEntity(ProductDTO, ProductEntity);
            ProductEntity = repository.save(ProductEntity);
            return new ProductDTO(ProductEntity);
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

    private void copyDtoToEntity(ProductDTO ProductDTO, Product ProductEntity) {

        ProductEntity.setName(ProductDTO.getName());
        ProductEntity.setDescription(ProductDTO.getDescription());
        ProductEntity.setDate(ProductDTO.getDate());
        ProductEntity.setPrice(ProductDTO.getPrice());
        ProductEntity.setImgUrl(ProductDTO.getImgUrl());

        ProductEntity.getCategories().clear();
        for(CategoryDTO catDTO : ProductDTO.getCategories()){
            Category Product = categoryRepository.getOne(catDTO.getId());
            ProductEntity.getCategories().add(Product);
        }

    }

}
