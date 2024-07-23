package com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity;

import com.eCommerce_Backend_Project.Backend_Project.data.user.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "cartItem")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "pid",referencedColumnName = "pid" ,nullable = false)
    private ProductEntity product;


    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public CartItemEntity(ProductEntity product,Integer quantity,UserEntity user){
        this.user = user;
        this.quantity = quantity;
        this.product = product;
    }

    public CartItemEntity(){

    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
