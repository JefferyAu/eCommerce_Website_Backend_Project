package com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity;

import com.eCommerce_Backend_Project.Backend_Project.data.transaction.status.TransactionStatus;
import com.eCommerce_Backend_Project.Backend_Project.data.user.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
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
    private LocalDateTime datetime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "total", nullable = false)
    private BigDecimal total = BigDecimal.valueOf(0);

    @Column(name= "Stripe_Session_Id")
    private String stripeSessionId;


    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public TransactionEntity(){

    }

    public TransactionEntity(UserEntity user, List<CartItemEntity> cartItemEntityList){
        this.user = user;
        this.datetime = LocalDateTime.now();
        setStatus(TransactionStatus.PREPARE);
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

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getStripeSessionId() {
        return stripeSessionId;
    }

    public void setStripeSessionId(String stripeSessionId) {
        this.stripeSessionId = stripeSessionId;
    }

    public void setTotal(List<CartItemEntity> cartItemEntityList) {
        //this.total = total;
        for(CartItemEntity cartItemEntity: cartItemEntityList){
            this.total = total.add(BigDecimal.valueOf(cartItemEntity.getQuantity()).multiply(cartItemEntity.getProduct().getPrice()));
        }
    }
}
