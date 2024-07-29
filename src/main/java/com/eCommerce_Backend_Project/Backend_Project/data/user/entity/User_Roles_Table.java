package com.eCommerce_Backend_Project.Backend_Project.data.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class User_Roles_Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer urid;

    @Column(name = "user_id")
    private  Integer userId;

    @Column(name = "role_id")
    private Integer roleId;
    

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUrid() {
        return urid;
    }

    public void setUrid(Integer urid) {
        this.urid = urid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
