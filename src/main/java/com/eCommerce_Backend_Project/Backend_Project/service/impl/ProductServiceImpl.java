package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.exception.ProductNotFoundException;
import com.eCommerce_Backend_Project.Backend_Project.repository.ProductRepository;
import com.eCommerce_Backend_Project.Backend_Project.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseData>getAllProduct(){
        List<ProductResponseData> productResponseDataList = new ArrayList<>();

        for(ProductEntity productEntity : productRepository.findAll()){
            ProductResponseData productResponseData = new ProductResponseData(productEntity);
            productResponseDataList.add(productResponseData);
        }

        return productResponseDataList;
    }

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

    public ProductEntity findBypid(Integer id){
        Optional<ProductEntity> productEntitylsit = productRepository.findBypid(id);
        try{
            if(productEntitylsit.isEmpty()){
                throw new ProductNotFoundException(id);
            }
            return productEntitylsit.get();
        }catch (Exception ex){
            logger.warn(ex.getMessage());
            throw  ex;
        }
    }
}
