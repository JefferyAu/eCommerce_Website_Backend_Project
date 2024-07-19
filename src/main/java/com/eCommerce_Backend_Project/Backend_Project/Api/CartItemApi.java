package com.eCommerce_Backend_Project.Backend_Project.Api;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject.CartItemResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.dto.CartItemResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.dto.SuccessCatItemResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.service.CartItemService;
import com.eCommerce_Backend_Project.Backend_Project.util.JwtUtil;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartItemApi {
    private final CartItemService cartItemService;

    public CartItemApi(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PutMapping("/{pid}/{quantity}")
    public SuccessCatItemResponseDto putCartItem(JwtAuthenticationToken jwt,
                            @PathVariable Integer pid,
                            @PathVariable Integer quantity){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        cartItemService.putCartItem(pid, quantity,firebaseUserData);
        SuccessCatItemResponseDto successCatItemResponseDto = new SuccessCatItemResponseDto("SUCCSS");
        return successCatItemResponseDto;
    }

    @GetMapping
    public List<CartItemResponseDto> getUserCart(){
        List<CartItemResponseData> cartItemResponseDataList = cartItemService.getUserCart();
        List<CartItemResponseDto> cartItemResponseDtoList = new ArrayList<>();

        for (CartItemResponseData cartItemResponseData: cartItemResponseDataList){
            CartItemResponseDto cartItemResponseDto = new CartItemResponseDto(cartItemResponseData);
            cartItemResponseDtoList.add(cartItemResponseDto);
        }
        return cartItemResponseDtoList;
    }
}
