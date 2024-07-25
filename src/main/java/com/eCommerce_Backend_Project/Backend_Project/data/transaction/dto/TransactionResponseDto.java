package com.eCommerce_Backend_Project.Backend_Project.data.transaction.dto;

import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.status.TransactionStatus;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.domainObject.TransactionProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.dto.TransactionProductResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"tid","buyerUid","datetime","status","total","items"})
public class TransactionResponseDto {
    private Integer tid;

    @JsonProperty("buyer_uid")
    private Integer buyerUid;

    @JsonFormat(pattern = "yyyyMMdd'T'HH:mm:ss")
    private LocalDateTime datetime;

    private TransactionStatus status;
    private BigDecimal total;
    private List<TransactionProductResponseDto> items = new ArrayList<>();

    public TransactionResponseDto(TransactionResponseData data){
        this.buyerUid = data.getUser().getUid();
        this.tid = data.getTid();
        this.datetime = data.getDatetime();
        this.status = data.getStatus();
        this.total = data.getTotal();
        for(TransactionProductResponseData transactionProductResponseData: data.getTransactionProductResponseDatalist()){
            TransactionProductResponseDto transactionProductResponseDto = new TransactionProductResponseDto(transactionProductResponseData);
            this.items.add(transactionProductResponseDto);
        }
    }

    public Integer getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(Integer buyerUid) {
        this.buyerUid = buyerUid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
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
