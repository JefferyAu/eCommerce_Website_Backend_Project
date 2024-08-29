package com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject;

import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.domainObject.TransactionProductResponseData;
import org.springframework.security.access.method.P;

import java.math.BigDecimal;

public class ProductResponseData {
    private Integer pid;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer stock;
    private String category;

    public ProductResponseData(ProductEntity entity){
        this.pid = entity.getPid();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.imageUrl = entity.getImageUrl();
        this.price = entity.getPrice();
        this.stock = entity.getStock();
        this.category = entity.getCategory();
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
