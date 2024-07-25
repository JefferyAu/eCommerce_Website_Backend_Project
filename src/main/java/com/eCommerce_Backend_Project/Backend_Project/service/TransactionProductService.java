package com.eCommerce_Backend_Project.Backend_Project.service;

import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.entity.TransactionProductEntity;

import java.util.List;

public interface TransactionProductService {
    List<TransactionProductEntity> addCartItemtoTransactionProduct(FirebaseUserData firebaseUserData, TransactionEntity tid);
    List<TransactionProductEntity> findTransactionProductList(TransactionEntity tid);
}
