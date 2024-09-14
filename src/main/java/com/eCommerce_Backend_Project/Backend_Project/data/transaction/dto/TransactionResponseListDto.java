package com.eCommerce_Backend_Project.Backend_Project.data.transaction.dto;

import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseListData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.status.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseListDto {
    private Integer tid;
    private LocalDateTime datetime;
    private TransactionStatus status;
    private BigDecimal total;


    public TransactionResponseListDto(TransactionResponseListData data) {
        this.tid = data.getTid();
        this.datetime = data.getDatetime();
        this.status = data.getStatus();
        this.total = data.getTotal();
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

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
