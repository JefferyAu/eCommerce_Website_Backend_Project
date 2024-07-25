package com.eCommerce_Backend_Project.Backend_Project.service;

import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;


public interface TransactionService {
    TransactionResponseData createTransaction(FirebaseUserData firebaseUserData);
    TransactionResponseData getTransactionDetailById(FirebaseUserData firebaseUserData, Integer tid);
    void updateTransactionStatus(FirebaseUserData firebaseUserData, Integer tid);
    TransactionResponseData finishTransaction(FirebaseUserData firebaseUserData, Integer tid);
}
