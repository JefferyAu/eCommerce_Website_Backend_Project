package com.eCommerce_Backend_Project.Backend_Project.service;

import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.user.entity.UserEntity;

public interface UserService {
    UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData);

    boolean getExistUserByFirebaseUserData(FirebaseUserData firebaseUserData);

    boolean userPermission(Integer uid);
}
