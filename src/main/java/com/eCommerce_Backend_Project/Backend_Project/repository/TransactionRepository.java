package com.eCommerce_Backend_Project.Backend_Project.repository;

import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionEntity,Integer> {
}
