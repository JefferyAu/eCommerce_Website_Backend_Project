package com.eCommerce_Backend_Project.Backend_Project.repository;

import com.eCommerce_Backend_Project.Backend_Project.data.User.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRespository extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByFirebaseUid(String firebaseUid);
}
