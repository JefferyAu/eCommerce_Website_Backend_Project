package com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity;

import com.eCommerce_Backend_Project.Backend_Project.data.user.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;

    @ManyToOne
    @JoinColumn(name = "buyer_uid", referencedColumnName = "uid",nullable = false)
    private UserEntity user;

    @Column(name = "datetime", nullable = false)
    private Timestamp datetime;

    @Column(name = "status" , nullable = false)
    private String status;

    @Column(name = "total", nullable = false)
    private BigDecimal total = BigDecimal.valueOf(0);

    public TransactionEntity(){

    }

    public TransactionEntity(UserEntity user, List<CartItemEntity> cartItemEntityList){
        this.user = user;
        this.datetime = Timestamp.valueOf(LocalDateTime.now());
        setStatus("PREPARE");
        setTotal(cartItemEntityList);
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(List<CartItemEntity> cartItemEntityList) {
        //this.total = total;
        for(CartItemEntity cartItemEntity: cartItemEntityList){
            this.total = total.add(BigDecimal.valueOf(cartItemEntity.getQuantity()).multiply(cartItemEntity.getProduct().getPrice()));
        }
    }
}
