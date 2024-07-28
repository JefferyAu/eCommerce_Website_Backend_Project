package com.eCommerce_Backend_Project.Backend_Project.service;

import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductRequestData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;

import java.util.List;

public interface ProductService {
    List<ProductResponseData> getAllProduct();
    ProductResponseData getProductbyid(Integer id);
    ProductResponseData addProduct(FirebaseUserData firebaseUserData, ProductRequestData data);
    ProductResponseData removeProduct(FirebaseUserData firebaseUserData, Integer pid);
    ProductResponseData updateProduct(FirebaseUserData firebaseUserData, Integer pid, ProductRequestData data);
    ProductEntity findBypid(Integer id);
    boolean isValidQuantity(Integer pid, Integer quantity);
    boolean deductStock(Integer pid, Integer quantity);
}
