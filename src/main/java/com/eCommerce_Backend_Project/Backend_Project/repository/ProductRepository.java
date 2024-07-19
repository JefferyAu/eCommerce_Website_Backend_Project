package com.eCommerce_Backend_Project.Backend_Project.repository;

import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
    Optional<ProductEntity> findBypid(Integer id);
}
