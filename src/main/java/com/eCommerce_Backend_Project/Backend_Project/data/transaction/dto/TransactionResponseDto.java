package com.eCommerce_Backend_Project.Backend_Project.data.transaction.dto;

import com.eCommerce_Backend_Project.Backend_Project.data.User.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.domainObject.TransactionProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.dto.TransactionProductResponseDto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionResponseDto {
    private Integer tid;
    private Integer user;
    private Timestamp datetime;
    private String status;
    private BigDecimal total;
    private List<TransactionProductResponseDto> items = new ArrayList<>();

    public TransactionResponseDto(TransactionResponseData data){
        this.user = data.getUser();
        this.tid = data.getTid();
        this.datetime = data.getDatetime();
        this.status = data.getStatus();
        this.total = data.getTotal();
        for(TransactionProductResponseData transactionProductResponseData: data.getTransactionProductResponseDatalist()){
            TransactionProductResponseDto transactionProductResponseDto = new TransactionProductResponseDto(transactionProductResponseData);
            this.items.add(transactionProductResponseDto);
        }
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
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

    public List<TransactionProductResponseDto> getItems() {
        return items;
    }

    public void setItems(List<TransactionProductResponseDto> items) {
        this.items = items;
    }
}
