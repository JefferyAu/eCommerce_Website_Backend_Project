package com.eCommerce_Backend_Project.Backend_Project.data.transaction.dto;

public class SuccessTransactionResponseDto {
    private String result;

    public SuccessTransactionResponseDto(){
        setResult("SUCCSS");
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
