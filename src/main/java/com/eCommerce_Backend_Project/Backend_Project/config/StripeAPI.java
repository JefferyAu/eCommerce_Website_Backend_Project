package com.eCommerce_Backend_Project.Backend_Project.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeAPI {
    @PostConstruct
    public void init() {
        // 設置 Stripe API 密鑰 Check Ec2 deploy
        Stripe.apiKey = "sk_test_51PtLhyBRyDFU5GVJwv8IZTGzo9rctL2v4Raru3j58Cn7GWWjLIsltq70eXiVLlBj4i4EkI5H6qCVSD2UzJ3MUG4d00p1BkHNgI";
    }
}
