package com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class FirebaseUserData {
    private String firebaseUid;
    private String email;

    public FirebaseUserData(JwtAuthenticationToken jwt){
        this.firebaseUid = (String)jwt.getTokenAttributes().get("user_id");
        this.email = (String) jwt.getTokenAttributes().get("email");
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
