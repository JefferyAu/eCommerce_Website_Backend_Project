package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.User.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject.CartItemResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.exception.CartItemException;
import com.eCommerce_Backend_Project.Backend_Project.repository.CartItemRepository;
import com.eCommerce_Backend_Project.Backend_Project.repository.ProductRepository;
import com.eCommerce_Backend_Project.Backend_Project.service.CartItemService;
import com.eCommerce_Backend_Project.Backend_Project.service.ProductService;
import com.eCommerce_Backend_Project.Backend_Project.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final UserService userService;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartItemServiceImpl(UserService userService,
                               ProductService productService,
                               CartItemRepository cartItemRepository,
                               ProductRepository productRepository) {
        this.userService = userService;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartItemResponseData> getUserCartByFirebaseUserData(FirebaseUserData firebaseUserData){

        UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);

        //List<CartItemEntity> cartItemEntityList = cartItemRepository.findAllByUser(loginUser);
        List<CartItemEntity> cartItemEntityList = findAllByUser(loginUser);

        List<CartItemResponseData> cartItemResponseDataList = new ArrayList<>();

        for (CartItemEntity cartItemEntity: cartItemEntityList){
            CartItemResponseData cartItemResponseData = new CartItemResponseData(cartItemEntity);
            cartItemResponseDataList.add(cartItemResponseData);
        }

        return cartItemResponseDataList;
    }
    @Override
    @Transactional
    public CartItemResponseData updateCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity){

        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            ProductEntity productEntity = productService.findBypid(pid);

            CartItemEntity cartItemEntity = findByProductAndUser(productEntity,loginUser);
            cartItemEntity.setQuantity(quantity);
            validateQuantity(cartItemEntity.getQuantity(),productEntity.getStock());
            CartItemResponseData cartItemResponseData = new CartItemResponseData(cartItemEntity);
            return cartItemResponseData;
        }catch (Exception ex){
            logger.warn("Update Cart Item: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void deleteCartItem(FirebaseUserData firebaseUserData, Integer pid){
        try {
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            ProductEntity productEntity = productService.findBypid(pid);
            CartItemEntity cartItemEntity = findByProductAndUser(productEntity,loginUser);
            cartItemRepository.delete(cartItemEntity);
        }catch (Exception ex){
            logger.warn("Delete Cart Item Failed: " + ex.getMessage());
            throw ex;
        }

    }

    @Override
    @Transactional
    public void putCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData){

        try {
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            ProductEntity productEntity = productService.findBypid(pid);

//            if (quantity <= 0 ){
//                throw new CartItemException("Quantity must be greater than zero");
//            }

            Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByProductAndUser(productEntity,userEntity);

            if(optionalCartItemEntity.isEmpty()){
               validateQuantity(quantity,productEntity.getStock());
                CartItemEntity cartItemExistEntity = new CartItemEntity(productEntity, quantity, userEntity);
                cartItemExistEntity.setProduct(productEntity);
                cartItemExistEntity.setUser(userEntity);
                cartItemExistEntity.setQuantity(quantity);
                cartItemRepository.save(cartItemExistEntity);
            }else {
                CartItemEntity cartItemEntity = optionalCartItemEntity.get();
                cartItemEntity.setQuantity(quantity + cartItemEntity.getQuantity());
                validateQuantity(cartItemEntity.getQuantity(),productEntity.getStock());
            }
        }catch (Exception ex){
            logger.warn("Add Cart Item Failed: " + ex.getMessage());
            throw ex;
        }
    }

    public void validateQuantity(Integer quantity, Integer stock){
        if(quantity > stock){
            throw new CartItemException("Quantity must be smaller than stock");
        }
    }

    public CartItemEntity findByProductAndUser(ProductEntity productEntity,UserEntity loginUser){
        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findByProductAndUser(productEntity,loginUser);

        if(cartItemEntityOptional.isEmpty()){
            //throw new CartItemException("Product is not exist in Cart");
            throw new CartItemException(String.format("Product is not exist in Cart: pid-%d, uid-%d", productEntity.getPid(),loginUser.getUid()));
        }
        return cartItemEntityOptional.get();
    }

    @Override
    public List<CartItemEntity> findAllByUser(UserEntity loginUser){
        List<CartItemEntity> cartItemEntityList = cartItemRepository.findAllByUser(loginUser);
        return cartItemEntityList;
    }

}
