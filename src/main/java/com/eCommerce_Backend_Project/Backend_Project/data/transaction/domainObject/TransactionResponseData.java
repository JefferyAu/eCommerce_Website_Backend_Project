package com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject;


import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.status.TransactionStatus;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.domainObject.TransactionProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.entity.TransactionProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.response.UserResponseData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionResponseData {
    private Integer tid;
    private UserResponseData user;
    private LocalDateTime datetime;
    private TransactionStatus status;
    private BigDecimal total;

    private List<TransactionProductResponseData> transactionProductResponseDatalist = new ArrayList<>() ;

    public TransactionResponseData(TransactionEntity entity, List<TransactionProductEntity> transactionProductEntityList){
        this.tid = entity.getTid();
        this.user = new UserResponseData(entity.getUser());
        this.datetime = entity.getDatetime();
        this.status = TransactionStatus.PREPARE;
        this.total = entity.getTotal();
        for(TransactionProductEntity transactionProductEntity: transactionProductEntityList){
            TransactionProductResponseData transactionProductResponseData = new TransactionProductResponseData(transactionProductEntity);
            transactionProductResponseDatalist.add(transactionProductResponseData);
        }
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserResponseData getUser() {
        return user;
    }

    public void setUser(UserResponseData user) {
        this.user = user;
    }
//    public Integer getUser() {
//        return user;
//    }
//
//    public void setUser(Integer user) {
//        this.user = user;
//    }

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

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<TransactionProductResponseData> getTransactionProductResponseDatalist() {
        return transactionProductResponseDatalist;
    }

    public void setTransactionProductResponseDatalist(List<TransactionProductResponseData> transactionProductResponseDatalist) {
        this.transactionProductResponseDatalist = transactionProductResponseDatalist;
    }
}
