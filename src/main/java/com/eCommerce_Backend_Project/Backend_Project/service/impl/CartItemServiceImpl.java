package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.User.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject.CartItemResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.exception.InvalidStockAmountException;
import com.eCommerce_Backend_Project.Backend_Project.repository.CartItemRepository;
import com.eCommerce_Backend_Project.Backend_Project.repository.ProductRepository;
import com.eCommerce_Backend_Project.Backend_Project.service.CartItemService;
import com.eCommerce_Backend_Project.Backend_Project.service.ProductService;
import com.eCommerce_Backend_Project.Backend_Project.service.UserService;
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

    public List<CartItemResponseData> getUserCart(){
        List<CartItemResponseData> cartItemResponseDataList = new ArrayList<>();

        for (CartItemEntity cartItemEntity: cartItemRepository.findAll()){
            CartItemResponseData cartItemResponseData = new CartItemResponseData(cartItemEntity);
            cartItemResponseDataList.add(cartItemResponseData);
        }

        return cartItemResponseDataList;
    }

    public void putCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData){

        UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
        Integer userEntityId = userEntity.getUid();
        //ProductResponseData productResponseData = productService.getProductbyid(pid);
        ProductEntity productEntity = productService.findBypid(pid);
        //Integer productId = productEntity.getPid();
        //CartItemEntity cartItemEntity = new CartItemEntity();
        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findByProductAndUid(productEntity,userEntityId);

        try {
            if (productEntity.getStock() > quantity) {
                if (cartItemEntity.isPresent()) {
                    cartItemEntity.get().setQuantity(quantity + cartItemEntity.get().getQuantity());
                    cartItemRepository.save(cartItemEntity.get());
                } else {
                    CartItemEntity cartItemExistEntity = new CartItemEntity(productEntity, quantity, userEntityId);
                    cartItemExistEntity.setProduct(productEntity);
                    cartItemExistEntity.setUid(userEntity.getUid());
                    cartItemExistEntity.setQuantity(quantity);
                    cartItemRepository.save(cartItemExistEntity);
                }
            } else {
                throw new InvalidStockAmountException(quantity);
            }
        }catch (Exception ex){
            logger.warn("Add Cart Stock" + ex.getMessage());
            throw new InvalidStockAmountException(quantity);
        }
    }
}
