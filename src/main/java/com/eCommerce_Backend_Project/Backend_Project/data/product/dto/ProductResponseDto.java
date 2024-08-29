package com.eCommerce_Backend_Project.Backend_Project.data.product.dto;

import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.domainObject.TransactionProductResponseData;

import java.math.BigDecimal;

public class ProductResponseDto {
    private Integer pid;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer stock;
    private String category;

    public ProductResponseDto(ProductResponseData data){
        this.pid = data.getPid();
        this.name = data.getName();
        this.description = data.getDescription();
        this.imageUrl = data.getImageUrl();
        this.price = data.getPrice();
        this.stock = data.getStock();
        this.category =data.getCategory();
    }

    public ProductResponseDto(TransactionProductResponseData data){
        this.pid = data.getPid();
        this.name = data.getName();
        this.description = data.getDescription();
        this.imageUrl = data.getImageUrl();
        this.price = data.getPrice();
        this.stock = data.getStock();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
