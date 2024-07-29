package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.user.entity.RolesTable;
import com.eCommerce_Backend_Project.Backend_Project.data.user.entity.UserEntity;
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

    @Override
    public boolean getExistUserByFirebaseUserData(FirebaseUserData firebaseUserData){
        Optional<UserEntity> existUser = userRespository.findByFirebaseUid(firebaseUserData.getFirebaseUid());
        if(existUser.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean userPermission(Integer uid){
        Optional<UserEntity> userEntity = userRespository.findByUid(uid);
        if(userEntity.isEmpty()){
            return false;
        }

        UserEntity user = userEntity.get();

        for(RolesTable rolesTable: user.getRoles()){
            if("admin".equals(rolesTable.getRoleName())){
                return true;
            }
        }
        return false;
    }
}
