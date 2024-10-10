package com.eCommerce_Backend_Project.Backend_Project.api;


import com.eCommerce_Backend_Project.Backend_Project.config.EnvConfig;
import com.eCommerce_Backend_Project.Backend_Project.data.product.domainObject.ProductResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.product.dto.AllProductResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseListData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.dto.SuccessTransactionResponseDto;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.dto.TransactionResponseDto;

import com.eCommerce_Backend_Project.Backend_Project.data.transaction.dto.TransactionResponseListDto;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.service.TransactionService;
import com.eCommerce_Backend_Project.Backend_Project.util.JwtUtil;
import com.stripe.exception.StripeException;
import jakarta.validation.constraints.Positive;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin({EnvConfig.DEV_BASE_URL, EnvConfig.PRO_BASE_RUL})
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


    @GetMapping("/all")
    public List<TransactionResponseListDto> getAllTransactionRecord(JwtAuthenticationToken jwt){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        List<TransactionResponseListData> transactionResponseListDataList = transactionService.getAllTransactionRecord(firebaseUserData);

        List<TransactionResponseListDto> allTransactionResponseList = new ArrayList<>();

        for(TransactionResponseListData transactionResponseListData: transactionResponseListDataList){
            TransactionResponseListDto transactionResponseListDto = new TransactionResponseListDto(transactionResponseListData);
            allTransactionResponseList.add(transactionResponseListDto);
        }
        return allTransactionResponseList;
    }


    @PatchMapping("/{tid}/pay")
    public String updateTransactionStatus(JwtAuthenticationToken jwt,@PathVariable @Positive Integer tid) throws StripeException {
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        return transactionService.updateTransactionStatus(firebaseUserData,tid);
    }

    @PatchMapping("/{tid}/finish")
    public TransactionResponseDto finishTransaction(JwtAuthenticationToken jwt, @PathVariable @Positive Integer tid) throws StripeException {
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
        TransactionResponseData transactionResponseData = transactionService.finishTransaction(firebaseUserData,tid);
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto(transactionResponseData);
        return transactionResponseDto;
    }
}
