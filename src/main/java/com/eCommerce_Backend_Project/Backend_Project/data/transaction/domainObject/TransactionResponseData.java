package com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject;


import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.domainObject.TransactionProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.entity.TransactionProductEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionResponseData {
    private Integer tid;
    private Integer user;
    private Timestamp datetime;
    private String status;
    private BigDecimal total;

    private List<TransactionProductResponseData> transactionProductResponseDatalist = new ArrayList<>() ;

    public TransactionResponseData(TransactionEntity entity, List<TransactionProductEntity> transactionProductEntityList){
        this.tid = entity.getTid();
        this.user = entity.getUser().getUid();
        this.datetime = entity.getDatetime();
        this.status = entity.getStatus();
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

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
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
