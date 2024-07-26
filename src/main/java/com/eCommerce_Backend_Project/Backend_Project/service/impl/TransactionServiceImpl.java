package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.status.TransactionStatus;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.user.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.entity.TransactionProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.exception.TransactionException;
import com.eCommerce_Backend_Project.Backend_Project.repository.TransactionProductRepository;
import com.eCommerce_Backend_Project.Backend_Project.repository.TransactionRepository;
import com.eCommerce_Backend_Project.Backend_Project.service.*;
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
    private final ProductService productService;

    public TransactionServiceImpl(UserService userService,
                                  CartItemService cartItemService,
                                  TransactionRepository transactionRepository,
                                  TransactionProductService transactionProductService,
                                  TransactionProductRepository transactionProductRepository,
                                  ProductService productService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.transactionRepository = transactionRepository;
        this.transactionProductService = transactionProductService;
        this.transactionProductRepository = transactionProductRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public TransactionResponseData createTransaction(FirebaseUserData firebaseUserData){
        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            List<CartItemEntity> cartItemEntityList = cartItemService.findAllByUser(loginUser);

            if(cartItemEntityList.isEmpty()){
                throw new TransactionException("Cart Item is empty");
            }

            TransactionEntity transactionEntity = new TransactionEntity(loginUser,cartItemEntityList);

            transactionEntity.setUser(loginUser);

            transactionRepository.save(transactionEntity);

            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.addCartItemtoTransactionProduct(firebaseUserData,transactionEntity);

            TransactionResponseData transactionResponseData = new TransactionResponseData(transactionEntity,transactionProductEntityList);
            return transactionResponseData;
        }catch (Exception ex){
            logger.warn("create transaction failed" + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public TransactionResponseData getTransactionDetailById(FirebaseUserData firebaseUserData, Integer tid){

        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = findTransactionUser(loginUser,tid);
            Optional<TransactionEntity> transactionTid = transactionRepository.findByTid(tid);
            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.findTransactionProductList(transactionTid.get());

            TransactionResponseData transactionResponseData = new TransactionResponseData(transactionEntity,transactionProductEntityList);
            return transactionResponseData;

        }catch (Exception ex){
            logger.warn("Get Transaction Detail failed: " + ex.getMessage());
            throw ex;
        }

    }

    @Override
    @Transactional
    public void updateTransactionStatus(FirebaseUserData firebaseUserData,Integer tid){
        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = findTransactionUser(loginUser,tid);
            if(transactionEntity.getStatus() != TransactionStatus.PREPARE){
                throw new TransactionException("Status error");
            }
            transactionEntity.setStatus(TransactionStatus.PROCESSING);
            transactionRepository.save(transactionEntity);
            Optional<TransactionEntity> transactionTid = transactionRepository.findByTid(tid);
            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.findTransactionProductList(transactionTid.get());
            for (TransactionProductEntity transactionProductEntity : transactionProductEntityList){
                ProductEntity productEntity = productService.findBypid(transactionProductEntity.getPid());
                if(!productService.isValidQuantity(transactionProductEntity.getPid(),transactionProductEntity.getQuantity())){
                    throw new TransactionException(String.format("Not enough stock: Pid%d Stock: %d",transactionProductEntity.getPid(),productEntity.getStock()));
                }
                productEntity.setStock(transactionProductEntity.getStock() - transactionProductEntity.getQuantity());
            }
        }catch (Exception ex){
            logger.warn("Update Transaction Status: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public TransactionResponseData finishTransaction(FirebaseUserData firebaseUserData, Integer tid){
        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = findTransactionUser(loginUser,tid);
            if(transactionEntity.getStatus() != TransactionStatus.PROCESSING){
                throw new TransactionException("Status error");
            }
            transactionEntity.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transactionEntity);

            Optional<TransactionEntity> transactionTid = transactionRepository.findByTid(tid);
            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.findTransactionProductList(transactionTid.get());
            for (TransactionProductEntity transactionProductEntity : transactionProductEntityList){
                  cartItemService.deleteCartItem(firebaseUserData,transactionProductEntity.getPid());
            }
            TransactionResponseData transactionResponseData = new TransactionResponseData(transactionEntity,transactionProductEntityList);
            return transactionResponseData;
        }catch (Exception ex){
            logger.warn("Finish Transaction: " + ex.getMessage());
            throw ex;
        }
    }

    public TransactionEntity findTransactionUser(UserEntity loginUser, Integer tid) {
        Optional<TransactionEntity> transactionEntity = transactionRepository.findByUserAndTid(loginUser, tid);

        if (transactionEntity.isEmpty()) {
            throw new TransactionException(String.format("No this transaction,uid-%s,tid-%d",loginUser.getUid(),tid));
        }
        return transactionEntity.get();
    }

}
