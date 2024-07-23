package com.eCommerce_Backend_Project.Backend_Project.api;


import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject.CartItemResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.dto.CartItemResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.dto.SuccessCatItemResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.service.CartItemService;
import com.eCommerce_Backend_Project.Backend_Project.service.ProductService;
import com.eCommerce_Backend_Project.Backend_Project.util.JwtUtil;
import jakarta.validation.constraints.Positive;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartItemApi {
    private final CartItemService cartItemService;
    private final ProductService productService;

    public CartItemApi(CartItemService cartItemService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.productService = productService;
    }


    @PutMapping("/{pid}/{quantity}")
    public SuccessCatItemResponseDto putCartItem(JwtAuthenticationToken jwt,
                            @PathVariable Integer pid,
                            @PathVariable @Positive Integer quantity){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        cartItemService.putCartItem(pid, quantity,firebaseUserData);
        return new SuccessCatItemResponseDto();
    }

    @PatchMapping("/{pid}/{quantity}")
    public CartItemResponseDto updateCartItem(JwtAuthenticationToken jwt,
                               @PathVariable Integer pid,
                               @PathVariable @Positive Integer quantity){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        CartItemResponseData cartItemResponseData = cartItemService.updateCartItem(firebaseUserData,pid,quantity);
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto(cartItemResponseData);
        return cartItemResponseDto;
    }

    @DeleteMapping("/{pid}")
    public SuccessCatItemResponseDto deleteCartItem(JwtAuthenticationToken jwt,
                               @PathVariable Integer pid){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        cartItemService.deleteCartItem(firebaseUserData,pid);
        return new SuccessCatItemResponseDto();
    }

    @GetMapping
    public List<CartItemResponseDto> getUserCartByFirebaseUserData(JwtAuthenticationToken jwt){
        List<CartItemResponseData> cartItemResponseDataList = cartItemService.getUserCartByFirebaseUserData(JwtUtil.getFirebaseUserData(jwt));
        List<CartItemResponseDto> cartItemResponseDtoList = new ArrayList<>();

        for (CartItemResponseData cartItemResponseData: cartItemResponseDataList){
            CartItemResponseDto cartItemResponseDto = new CartItemResponseDto( cartItemResponseData);
            cartItemResponseDtoList.add(cartItemResponseDto);
        }
        return cartItemResponseDtoList;
    }
}
