package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.User.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.entity.TransactionProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.repository.TransactionProductRepository;
import com.eCommerce_Backend_Project.Backend_Project.service.CartItemService;
import com.eCommerce_Backend_Project.Backend_Project.service.TransactionProductService;
import com.eCommerce_Backend_Project.Backend_Project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionProductServiceImpl implements TransactionProductService {

    private final CartItemService cartItemService;
    private final UserService  userService;
    private final TransactionProductRepository transactionProductRepository;

    public TransactionProductServiceImpl(CartItemService cartItemService,UserService userService,TransactionProductRepository transactionProductRepository) {
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.transactionProductRepository = transactionProductRepository;
    }

    @Override
    public List<TransactionProductEntity> addCartItemtoTransactionProduct(FirebaseUserData firebaseUserData,TransactionEntity tid){
        UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
        List<CartItemEntity> cartItemEntityList = cartItemService.findAllByUser(loginUser);

        List<TransactionProductEntity> transactionProductEntityList = new ArrayList<>();

        for(CartItemEntity cartItemEntity: cartItemEntityList){
            TransactionProductEntity transactionProductEntity = new TransactionProductEntity(tid,cartItemEntity);
            transactionProductEntity.setTid(tid);
            transactionProductEntity.setPid(cartItemEntity.getProduct().getPid());
            transactionProductEntity.setName(cartItemEntity.getProduct().getName());
            transactionProductEntity.setDescription(cartItemEntity.getProduct().getDescription());
            transactionProductEntity.setImageUrl(cartItemEntity.getProduct().getImageUrl());
            transactionProductEntity.setPrice(cartItemEntity.getProduct().getPrice());
            transactionProductEntity.setStock(cartItemEntity.getProduct().getStock());
            transactionProductEntity.setQuantity(cartItemEntity.getQuantity());
            transactionProductRepository.save(transactionProductEntity);
            transactionProductEntityList.add(transactionProductEntity);
        }
      return transactionProductEntityList;
    }

}
