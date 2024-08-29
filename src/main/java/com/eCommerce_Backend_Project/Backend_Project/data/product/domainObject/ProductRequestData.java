package com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject;


import com.eCommerce_Backend_Project.Backend_Project.data.product.dto.ProductRequestDto;

import java.math.BigDecimal;

public class ProductRequestData {

    private String name;

    private String description;

    private String imageUrl;

    private BigDecimal price;

    private Integer stock;

    private String category;


    public ProductRequestData(ProductRequestDto dto){
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.imageUrl = dto.getImageUrl();
        this.price = dto.getPrice();
        this.stock = dto.getStock();
        this.category = dto.getCategory();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
