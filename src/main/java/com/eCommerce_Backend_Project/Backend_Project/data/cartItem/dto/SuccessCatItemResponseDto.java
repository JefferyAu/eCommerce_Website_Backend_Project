package com.eCommerce_Backend_Project.Backend_Project.data.cartItem.dto;

public class SuccessCatItemResponseDto {
    private String result;

    public SuccessCatItemResponseDto(String result){
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
