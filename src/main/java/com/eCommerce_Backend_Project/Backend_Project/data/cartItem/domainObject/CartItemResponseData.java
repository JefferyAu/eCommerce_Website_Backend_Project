package com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject;

import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.response.UserResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;

public class CartItemResponseData {
    private Integer cid;
    private UserResponseData user;
    private Integer quantity;
    private ProductResponseData product;

    public CartItemResponseData(CartItemEntity cartItemEntity){
        this.cid = cartItemEntity.getCid();
        this.product = new ProductResponseData(cartItemEntity.getProduct());
        this.quantity = cartItemEntity.getQuantity();
        this.user = new UserResponseData(cartItemEntity.getUser());
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public UserResponseData getUser() {
        return user;
    }

    public void setUser(UserResponseData user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductResponseData getProduct() {
        return product;
    }

    public void setProduct(ProductResponseData product) {
        this.product = product;
    }
}
