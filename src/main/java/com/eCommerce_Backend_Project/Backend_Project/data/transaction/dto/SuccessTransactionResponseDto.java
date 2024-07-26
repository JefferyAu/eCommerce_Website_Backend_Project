package com.eCommerce_Backend_Project.Backend_Project.data.transaction.dto;

import com.eCommerce_Backend_Project.Backend_Project.data.transaction.status.TransactionStatus;

public class SuccessTransactionResponseDto {
    private TransactionStatus result;

    public SuccessTransactionResponseDto(){
        setResult(TransactionStatus.SUCCESS);
    }

    public TransactionStatus getResult() {
        return result;
    }

    public void setResult(TransactionStatus result) {
        this.result = result;
    }
}
