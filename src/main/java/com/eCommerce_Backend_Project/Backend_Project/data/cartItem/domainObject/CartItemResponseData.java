package com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject;

import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import jakarta.persistence.Column;

public class CartItemResponseData {
    private Integer cid;
    private Integer uid;
    private Integer quantity;
    private ProductResponseData product;

    public CartItemResponseData(CartItemEntity cartItemEntity){
        this.cid = cartItemEntity.getCid();
        this.uid = cartItemEntity.getUid();
        this.quantity = cartItemEntity.getQuantity();
        this.product = new ProductResponseData((cartItemEntity.getProduct()));
    }

    public ProductResponseData getProduct() {
        return product;
    }

    public void setProduct(ProductResponseData product) {
        this.product = product;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
