package com.eCommerce_Backend_Project.Backend_Project.service;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject.CartItemResponseData;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CartItemService {
    @Transactional
    CartItemResponseData updateCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);
    public void deleteCartItem(FirebaseUserData firebaseUserData, Integer pid);
    public void putCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData);
    List<CartItemResponseData> getUserCartByFirebaseUserData(FirebaseUserData firebaseUserData);
}
