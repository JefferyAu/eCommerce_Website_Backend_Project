package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.User.domainObject.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.User.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.repository.UserRespository;
import com.eCommerce_Backend_Project.Backend_Project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRespository userRespository;

    public UserServiceImpl(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    public UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData){
        Optional<UserEntity> optionalUserEntity = userRespository.findByFirebaseUid(firebaseUserData.getFirebaseUid());
        if(optionalUserEntity.isEmpty()){
            UserEntity userEntity = new UserEntity(firebaseUserData);
            return userRespository.save(userEntity);
        } else {
            return  optionalUserEntity.get();
        }

    }
}
