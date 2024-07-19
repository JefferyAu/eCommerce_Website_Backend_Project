package com.eCommerce_Backend_Project.Backend_Project.data.cartItem.dto;

import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject.CartItemResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.dto.ProductResponseDto;

public class CartItemResponseDto {
    private Integer cid;
    private Integer uid;
    private Integer quantity;
    private ProductResponseDto product;


    public CartItemResponseDto(CartItemResponseData data){
        this.cid = data.getCid();
        this.uid = data.getUid();
        this.quantity = data.getQuantity();
        this.product = new ProductResponseDto(data.getProduct());
    }

    public ProductResponseDto getProduct() {
        return product;
    }

    public void setProduct(ProductResponseDto product) {
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
