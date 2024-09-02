package com.eCommerce_Backend_Project.Backend_Project.api;

import com.eCommerce_Backend_Project.Backend_Project.config.DevConfig;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductRequestData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.dto.ProductRequestDto;
import com.eCommerce_Backend_Project.Backend_Project.data.product.dto.ProductResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.service.ProductService;
import com.eCommerce_Backend_Project.Backend_Project.util.JwtUtil;
import jakarta.validation.constraints.Positive;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({DevConfig.DEV_BASE_URL,DevConfig.PRO_BASE_RUL})
@RequestMapping("/product")
public class AdminApi {

    private final ProductService productService;

    public AdminApi(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponseDto addProduct(JwtAuthenticationToken jwt, @RequestBody ProductRequestDto dto){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        ProductRequestData productRequestData = new ProductRequestData(dto);
        ProductResponseData productResponseData = productService.addProduct(firebaseUserData,productRequestData);
        ProductResponseDto productResponseDto = new ProductResponseDto(productResponseData);
        return productResponseDto ;
    }

    @DeleteMapping("{pid}")
    public ProductResponseDto removeProduct(JwtAuthenticationToken jwt,
                              @PathVariable @Positive Integer pid){
         FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
         ProductResponseData productResponseData = productService.removeProduct(firebaseUserData,pid);
         ProductResponseDto productResponseDto = new ProductResponseDto(productResponseData);
         return productResponseDto;
    }

    @PutMapping("{pid}")
    public ProductResponseDto updateProduct(JwtAuthenticationToken jwt,
                              @PathVariable @Positive Integer pid,
                              @RequestBody ProductRequestDto dto){
    FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
    ProductRequestData productRequestData = new ProductRequestData(dto);
    ProductResponseData productResponseData = productService.updateProduct(firebaseUserData, pid,productRequestData);
    ProductResponseDto productResponseDto = new ProductResponseDto(productResponseData);
    return productResponseDto;
    }
}
