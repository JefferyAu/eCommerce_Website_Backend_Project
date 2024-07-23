package com.eCommerce_Backend_Project.Backend_Project.api;


import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.dto.SuccessCatItemResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.dto.TransactionResponseDto;

import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.service.TransactionService;
import com.eCommerce_Backend_Project.Backend_Project.util.JwtUtil;
import jakarta.validation.constraints.Positive;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionApi {

    private final TransactionService transactionService;

    public TransactionApi(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/prepare")
    public TransactionResponseDto createTransaction(JwtAuthenticationToken jwt){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        TransactionResponseData transactionResponseData = transactionService.createTransaction(firebaseUserData);
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto(transactionResponseData);
        return transactionResponseDto;
    }

    @GetMapping("/{tid}")
    public TransactionResponseDto getTransactionDetailById(JwtAuthenticationToken jwt,
                                         @PathVariable @Positive Integer tid){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        TransactionResponseData transactionResponseData = transactionService.getTransactionDetailById(firebaseUserData,tid);
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto(transactionResponseData);
        return transactionResponseDto;
    }

    @PatchMapping("/{tid}/pay")
    public SuccessCatItemResponseDto updateTransactionStatus(JwtAuthenticationToken jwt,@PathVariable @Positive Integer tid){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        transactionService.updateTransactionStatus(firebaseUserData,tid);
        return new SuccessCatItemResponseDto();
    }
}
