package com.eCommerce_Backend_Project.Backend_Project.service;

import com.eCommerce_Backend_Project.Backend_Project.data.domainObject.ProductResponseData;

import java.util.List;

public interface ProductService {
    List<ProductResponseData> getAllProduct();
    ProductResponseData getProductbyid(Integer id);
}
