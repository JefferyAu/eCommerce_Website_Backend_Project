package com.eCommerce_Backend_Project.Backend_Project.repository;

import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.entity.TransactionProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionProductRepository extends CrudRepository<TransactionProductEntity, Integer> {

}
