package com.eCommerce_Backend_Project.Backend_Project.service;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.User.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject.CartItemResponseData;

import java.util.List;

public interface CartItemService {
    public void putCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData);
    List<CartItemResponseData> getUserCart();
}
