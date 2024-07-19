package com.eCommerce_Backend_Project.Backend_Project.service;

import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductResponseData> getAllProduct();
    ProductResponseData getProductbyid(Integer id);
    ProductEntity findBypid(Integer id);
}
