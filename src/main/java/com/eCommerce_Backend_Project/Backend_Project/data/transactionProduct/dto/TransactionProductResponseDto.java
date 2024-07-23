package com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.dto;

import com.eCommerce_Backend_Project.Backend_Project.data.product.dto.ProductResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.domainObject.TransactionProductResponseData;

import java.math.BigDecimal;

public class TransactionProductResponseDto {
    private Integer tpid;
    private ProductResponseDto product;
    private Integer quantity;
    private BigDecimal subtotal;

    public TransactionProductResponseDto(TransactionProductResponseData data){
        this.tpid = data.getTpid();
        this.product = new ProductResponseDto(data);
        this.quantity = data.getQuantity();
        setSubtotal(data);
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(TransactionProductResponseData data) {
        this.subtotal = BigDecimal.valueOf(data.getQuantity()).multiply(data.getPrice());
    }

    public ProductResponseDto getProduct() {
        return product;
    }

    public void setProduct(ProductResponseDto product) {
        this.product = product;
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
