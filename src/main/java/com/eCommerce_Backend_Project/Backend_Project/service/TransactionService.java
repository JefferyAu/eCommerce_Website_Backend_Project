package com.eCommerce_Backend_Project.Backend_Project.service;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import org.springframework.stereotype.Service;


public interface TransactionService {
    TransactionResponseData createTransaction(FirebaseUserData firebaseUserData);
}
