package com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.entity;

import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transaction_product")
public class TransactionProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tpid;

    @ManyToOne
    @JoinColumn(name = "tid",referencedColumnName = "tid", nullable = false)
    private TransactionEntity tid;

    @Column(name = "pid",nullable = false)
    private Integer pid;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "quantity")
    private Integer quantity;

    public TransactionProductEntity(){

    }

    public TransactionProductEntity(TransactionEntity tid, CartItemEntity cartItemEntity){
        this.tid = tid;
        this.pid = cartItemEntity.getProduct().getPid();
        this.name = cartItemEntity.getProduct().getName();
        this.description = cartItemEntity.getProduct().getDescription();
        this.imageUrl = cartItemEntity.getProduct().getImageUrl();
        this.price = cartItemEntity.getProduct().getPrice();
        this.stock = cartItemEntity.getProduct().getStock();
        this.quantity = cartItemEntity.getQuantity();
    }


    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public TransactionEntity getTid() {
        return tid;
    }

    public void setTid(TransactionEntity tid) {
        this.tid = tid;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
