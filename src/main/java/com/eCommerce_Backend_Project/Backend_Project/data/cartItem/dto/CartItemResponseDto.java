package com.eCommerce_Backend_Project.Backend_Project.data.cartItem.dto;

import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject.CartItemResponseData;

import java.math.BigDecimal;

public class CartItemResponseDto {
    private Integer pid;
    private String name;
    private String imageUrl;
    private BigDecimal price;
    private Integer cartQuantity;
    private Integer stock;


    public CartItemResponseDto(CartItemResponseData data){
        this.pid = data.getProduct().getPid();
        this.name = data.getProduct().getName();
        this.imageUrl = data.getProduct().getImageUrl();
        this.price = data.getProduct().getPrice();
        this.cartQuantity = data.getQuantity();
        this.stock = data.getProduct().getStock();
    }

    public Integer getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Integer cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
