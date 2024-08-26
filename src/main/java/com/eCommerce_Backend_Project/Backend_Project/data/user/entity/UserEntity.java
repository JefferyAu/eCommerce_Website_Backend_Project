package com.eCommerce_Backend_Project.Backend_Project.data.user.entity;

import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import jakarta.persistence.*;

import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "rid")
    )

    private List<RolesTable> roles;

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

    public List<RolesTable> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesTable> roles) {
        this.roles = roles;
    }
}
