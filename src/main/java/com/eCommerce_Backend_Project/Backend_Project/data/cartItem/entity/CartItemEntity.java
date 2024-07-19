package com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity;

import com.eCommerce_Backend_Project.Backend_Project.data.User.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.domainObject.CartItemResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "cartItem")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    //@Column(name = "pid", nullable = false)
    //private Integer pid;

    @Column(name = "uid", nullable = false)
    private Integer uid;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "pid",referencedColumnName = "pid" ,nullable = false)
    private ProductEntity product;


//    @ManyToOne
//    @JoinColumn(name ="uid", referencedColumnName = "uid",nullable = false)
//    private UserEntity userEntity;

//    public UserEntity getUserEntity() {
//        return userEntity;
//    }
//
//    public void setUserEntity(UserEntity userEntity) {
//        this.userEntity = userEntity;
//    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public CartItemEntity(ProductEntity product,Integer quantity,Integer userEntityId){
        this.uid = userEntityId;
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


    public Integer getUid() {
        return this.uid;
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
