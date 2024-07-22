package com.eCommerce_Backend_Project.Backend_Project.Api;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.dto.TransactionResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.service.TransactionService;
import com.eCommerce_Backend_Project.Backend_Project.util.JwtUtil;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
