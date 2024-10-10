package com.eCommerce_Backend_Project.Backend_Project.data.product.entity;

import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductRequestData;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "category",nullable = false)
    private String category;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "stripe_price_id")
    private String StripePriceID;


    public ProductEntity(){

    }

    public ProductEntity(ProductRequestData data){
        this.name = data.getName();
        this.description = data.getDescription();
        this.imageUrl = data.getImageUrl();
        this.price = data.getPrice();
        this.stock = data.getStock();
        this.category = data.getCategory();
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
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStripePriceID() {
        return StripePriceID;
    }

    public void setStripePriceID(String stripePriceID) {
        StripePriceID = stripePriceID;
    }
}
