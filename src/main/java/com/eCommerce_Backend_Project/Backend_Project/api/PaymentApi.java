package com.eCommerce_Backend_Project.Backend_Project.api;

import com.eCommerce_Backend_Project.Backend_Project.config.EnvConfig;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.service.TransactionService;
import com.eCommerce_Backend_Project.Backend_Project.util.JwtUtil;
import jakarta.validation.constraints.Positive;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/api")
//@CrossOrigin({EnvConfig.DEV_BASE_URL, EnvConfig.PRO_BASE_RUL})
@CrossOrigin(
        origins = {"https://shop.betasolution.online", "http://localhost:5173"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowCredentials = "true"
)
public class PaymentApi {
    private final TransactionService transactionService;

    public PaymentApi(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

//    @PostMapping("/create-checkout-session/{tid}")
//    public String createCheckoutSession(JwtAuthenticationToken jwt, @PathVariable @Positive Integer tid){
//        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwt);
//        return  transactionService.createCheckoutSession(firebaseUserData,tid);
//    }

//    @Value("${stripe.apikey}")
//    public String stripeKey;
//
//    @PostMapping("/createCustomer")
//    public UserResponseDto createUser(JwtAuthenticationToken jwtToken) throws com.stripe.exception.StripeException{
//        Stripe.apiKey = stripeKey;
//
//        UserResponseDto loginUser = new UserResponseDto(jwtToken);
//        CustomerCreateParams params =
//                CustomerCreateParams.builder()
//                        .setEmail(loginUser.getEmail())
//                        .build();
//        Customer customer = Customer.create(params);
//        loginUser.setFirebaseUid(customer.getId());
//        return loginUser;
//    }

}
