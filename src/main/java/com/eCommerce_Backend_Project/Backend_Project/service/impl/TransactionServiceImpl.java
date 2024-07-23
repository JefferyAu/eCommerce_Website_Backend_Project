package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.user.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.entity.TransactionProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.exception.TransactionException;
import com.eCommerce_Backend_Project.Backend_Project.repository.TransactionProductRepository;
import com.eCommerce_Backend_Project.Backend_Project.repository.TransactionRepository;
import com.eCommerce_Backend_Project.Backend_Project.service.CartItemService;
import com.eCommerce_Backend_Project.Backend_Project.service.TransactionProductService;
import com.eCommerce_Backend_Project.Backend_Project.service.TransactionService;
import com.eCommerce_Backend_Project.Backend_Project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final UserService userService;
    private final CartItemService cartItemService;
    private final TransactionRepository transactionRepository;
    private final TransactionProductService transactionProductService;
    private final TransactionProductRepository transactionProductRepository;

    public TransactionServiceImpl(UserService userService,
                                  CartItemService cartItemService,
                                  TransactionRepository transactionRepository,
                                  TransactionProductService transactionProductService,
                                  TransactionProductRepository transactionProductRepository) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.transactionRepository = transactionRepository;
        this.transactionProductService = transactionProductService;
        this.transactionProductRepository = transactionProductRepository;
    }

    @Override
    @Transactional
    public TransactionResponseData createTransaction(FirebaseUserData firebaseUserData){
        UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
        List<CartItemEntity> cartItemEntityList = cartItemService.findAllByUser(loginUser);
        TransactionEntity transactionEntity = new TransactionEntity(loginUser,cartItemEntityList);

        transactionEntity.setUser(loginUser);

        transactionRepository.save(transactionEntity);

        List<TransactionProductEntity> transactionProductEntityList = transactionProductService.addCartItemtoTransactionProduct(firebaseUserData,transactionEntity);

        TransactionResponseData transactionResponseData = new TransactionResponseData(transactionEntity,transactionProductEntityList);
        return transactionResponseData;
    }

    @Override
    public TransactionResponseData getTransactionDetailById(FirebaseUserData firebaseUserData, Integer tid){

        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = findTransactionUser(loginUser,tid);
            Optional<TransactionEntity> transactionTid = transactionRepository.findByTid(tid);

            List<TransactionProductEntity> transactionProductEntityList = transactionProductRepository.findAllByTid(transactionTid.get());

            TransactionResponseData transactionResponseData = new TransactionResponseData(transactionEntity,transactionProductEntityList);
            return transactionResponseData;

        }catch (Exception ex){
            logger.warn("Get Transaction Detail failed: " + ex.getMessage());
            throw ex;
        }

    }

    @Override
    public void updateTransactionStatus(FirebaseUserData firebaseUserData,Integer tid){
        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = findTransactionUser(loginUser,tid);
            transactionEntity.setStatus("PROCESSING");
            transactionRepository.save(transactionEntity);
        }catch (Exception ex){
            logger.warn("Update Transaction Status:" + ex.getMessage());
            throw ex;
        }
    }

    public TransactionEntity findTransactionUser(UserEntity loginUser,Integer tid){
        Optional<TransactionEntity> transactionEntity = transactionRepository.findByUserAndTid(loginUser,tid);

        if(transactionEntity.isEmpty()){
            throw new TransactionException("No this transaction");
        }
        return transactionEntity.get();
    }
}
