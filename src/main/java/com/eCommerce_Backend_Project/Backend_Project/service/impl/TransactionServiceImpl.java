package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.User.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.entity.TransactionProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.repository.TransactionRepository;
import com.eCommerce_Backend_Project.Backend_Project.service.CartItemService;
import com.eCommerce_Backend_Project.Backend_Project.service.TransactionProductService;
import com.eCommerce_Backend_Project.Backend_Project.service.TransactionService;
import com.eCommerce_Backend_Project.Backend_Project.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final CartItemService cartItemService;
    private final TransactionRepository transactionRepository;
    private final TransactionProductService transactionProductService;


    public TransactionServiceImpl(UserService userService,
                                  CartItemService cartItemService,
                                  TransactionRepository transactionRepository,
                                  TransactionProductService transactionProductService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.transactionRepository = transactionRepository;
        this.transactionProductService = transactionProductService;
    }

    @Override
    @Transactional
    public TransactionResponseData createTransaction(FirebaseUserData firebaseUserData){
        UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);

        TransactionEntity transactionEntity = new TransactionEntity(loginUser);

        transactionEntity.setUser(loginUser);

        transactionRepository.save(transactionEntity);
        List<TransactionProductEntity> transactionProductEntityList = transactionProductService.addCartItemtoTransactionProduct(firebaseUserData,transactionEntity);
//        for (TransactionProductEntity transactionProductEntity: transactionProductEntityList){
//            System.out.println("Transaction Product Entity:");
//            System.out.println("Tid: " + transactionProductEntity.getTid());
//            System.out.println("Product ID: " + transactionProductEntity.getPid());
//            System.out.println("name: " + transactionProductEntity.getName());
//            System.out.println("Description: " + transactionProductEntity.getDescription());
//            System.out.println("Price: " + transactionProductEntity.getPrice());
//            System.out.println("Stock: " + transactionProductEntity.getStock());
//            System.out.println("Quantity: " + transactionProductEntity.getQuantity());
//        }

        TransactionResponseData transactionResponseData = new TransactionResponseData(transactionEntity,transactionProductEntityList);
        return transactionResponseData;
    }
}
