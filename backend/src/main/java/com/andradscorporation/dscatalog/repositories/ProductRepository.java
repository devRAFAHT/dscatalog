package com.andradscorporation.dscatalog.repositories;

import com.andradscorporation.dscatalog.entities.Category;
import com.andradscorporation.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
