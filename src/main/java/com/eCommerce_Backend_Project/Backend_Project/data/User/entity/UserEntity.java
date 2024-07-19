package com.eCommerce_Backend_Project.Backend_Project.data.User.entity;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.FirebaseUserData;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column(unique = true,nullable = false)
    private  String firebaseUid;

    @Column(unique = true, nullable = false)
    private String email;

    public UserEntity() {
    }

    public UserEntity(FirebaseUserData firebaseUserData){
        this.firebaseUid = firebaseUserData.getFirebaseUid();
        this.email = firebaseUserData.getEmail();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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
