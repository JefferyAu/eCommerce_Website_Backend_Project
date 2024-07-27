package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductRequestData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.exception.ProductException;
import com.eCommerce_Backend_Project.Backend_Project.exception.ProductNotFoundException;
import com.eCommerce_Backend_Project.Backend_Project.exception.UserException;
import com.eCommerce_Backend_Project.Backend_Project.repository.ProductRepository;
import com.eCommerce_Backend_Project.Backend_Project.service.ProductService;
import com.eCommerce_Backend_Project.Backend_Project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    public ProductServiceImpl(ProductRepository productRepository,
                              UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public List<ProductResponseData>getAllProduct(){
        List<ProductResponseData> productResponseDataList = new ArrayList<>();

        for(ProductEntity productEntity : productRepository.findAll()){
            ProductResponseData productResponseData = new ProductResponseData(productEntity);
            productResponseDataList.add(productResponseData);
        }

        return productResponseDataList;
    }

    @Override
    public ProductResponseData getProductbyid(Integer id){
       try{
        ProductEntity productEntity = findBypid(id);
        ProductResponseData productResponseData = new ProductResponseData(productEntity);
        return productResponseData;
       }catch (Exception ex){
           logger.warn(" Get Product ID: " + ex.getMessage());
           throw ex;
       }
    }

    @Override
    public ProductResponseData addProduct(FirebaseUserData firebaseUserData,ProductRequestData data){
        try{

        if (!adminPermission(firebaseUserData.getFirebaseUid())){
            throw new UserException("You don't have permission to add product");
        }

        if(findByProductName(data.getName())){
            throw new ProductException("Product name is already exist");
        }

        ProductEntity productEntity = new ProductEntity(data);
        productEntity.setName(data.getName());
        productEntity.setDescription(data.getDescription());
        productEntity.setImageUrl(data.getImageUrl());
        productEntity.setPrice(data.getPrice());
        productEntity.setStock(data.getStock());
        productRepository.save(productEntity);

        ProductResponseData productResponseData = new ProductResponseData(productEntity);
        return productResponseData;

        }catch (Exception ex){
            logger.warn("Add Proudct: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public ProductResponseData removeProduct(FirebaseUserData firebaseUserData,Integer pid){
        try {

            if (!adminPermission(firebaseUserData.getFirebaseUid())){
                throw new UserException("You don't have permission to remove product");
            }

            ProductEntity productEntity = findBypid(pid);
            productRepository.delete(productEntity);

            ProductResponseData productResponseData = new ProductResponseData(productEntity);
            return productResponseData;

        }catch (Exception ex){
            logger.warn("Remove Product: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public ProductResponseData updateProduct(FirebaseUserData firebaseUserData, Integer pid, ProductRequestData data){
        try{
            ProductEntity productEntity = findBypid(pid);

            if(!adminPermission(firebaseUserData.getFirebaseUid())){
                throw new UserException("You don't have permission to update product");
            }

//            if(findByProductName(data.getName())){
//                throw new ProductException("Product name is already exist");
//            }

            productEntity.setName(data.getName());
            productEntity.setDescription(data.getDescription());
            productEntity.setImageUrl(data.getImageUrl());
            productEntity.setPrice(data.getPrice());
            productEntity.setStock(data.getStock());
            productRepository.save(productEntity);

            ProductResponseData productResponseData = new ProductResponseData(productEntity);
            return productResponseData;

        }catch (Exception ex){
            logger.warn("Update Product: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public ProductEntity findBypid(Integer id){
        Optional<ProductEntity> productEntity = productRepository.findBypid(id);
        try{
            if(productEntity.isEmpty()){
                throw new ProductNotFoundException(id);
            }
            return productEntity.get();
        }catch (Exception ex){
            logger.warn(ex.getMessage());
            throw  ex;
        }
    }

    public boolean findByProductName(String name){
        Optional<ProductEntity> productName = productRepository.findByname(name);
        if (productName.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidQuantity(Integer pid, Integer quantity){
        ProductEntity productEntity = findBypid(pid);
        if(quantity < 1){
            return false;
        }else if(quantity > productEntity.getStock()){
            return false;
        }
        return true;
    }

    public boolean adminPermission(String uid){
        if (uid.equals("eVBvl8vc8jgFseJANOaZv04OnaX2")){
            return true;
        }else {
           return false;
        }
    }
}
