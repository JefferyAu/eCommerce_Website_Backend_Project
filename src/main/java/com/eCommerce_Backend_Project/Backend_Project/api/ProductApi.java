package com.eCommerce_Backend_Project.Backend_Project.api;

import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductRequestData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.dto.AllProductResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.data.product.dto.ProductRequestDto;
import com.eCommerce_Backend_Project.Backend_Project.data.product.dto.ProductResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.service.ProductService;
import com.eCommerce_Backend_Project.Backend_Project.util.JwtUtil;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/product")
public class ProductApi {

    private final ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping
//    public List<AllProductResponseDto>  getAllProduct(){
//        List<ProductResponseData> productResponseDataList = productService.getAllProduct();
//
//        List<AllProductResponseDto> allProductResponseDtoList = new ArrayList<>();
//        for(ProductResponseData productResponseData : productResponseDataList){
//            AllProductResponseDto allproductResponseDto = new AllProductResponseDto(productResponseData);
//            allProductResponseDtoList.add(allproductResponseDto);
//        }
//        return allProductResponseDtoList;
///    }

    @GetMapping("{id}")
    public ProductResponseDto getProductbyid(@PathVariable Integer id){
        ProductResponseData productResponseData = productService.getProductbyid(id);
        ProductResponseDto productResponseDto = new ProductResponseDto(productResponseData);
        return productResponseDto;
    }

}
