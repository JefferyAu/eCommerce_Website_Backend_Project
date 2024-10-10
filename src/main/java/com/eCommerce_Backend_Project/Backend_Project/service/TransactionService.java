package com.eCommerce_Backend_Project.Backend_Project.service;

import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseListData;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import com.stripe.exception.StripeException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TransactionService {
    TransactionResponseData createTransaction(FirebaseUserData firebaseUserData);
    TransactionResponseData getTransactionDetailById(FirebaseUserData firebaseUserData, Integer tid);

    List<TransactionResponseListData> getAllTransactionRecord(FirebaseUserData firebaseUserData);

    String updateTransactionStatus(FirebaseUserData firebaseUserData, Integer tid) throws StripeException;

//    String createCheckoutSession(FirebaseUserData firebaseUserData, Integer tid);

    TransactionResponseData finishTransaction(FirebaseUserData firebaseUserData, Integer tid) throws StripeException;
}
