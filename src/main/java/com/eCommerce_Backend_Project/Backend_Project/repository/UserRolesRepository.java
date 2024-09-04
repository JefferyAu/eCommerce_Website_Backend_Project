package com.eCommerce_Backend_Project.Backend_Project.repository;

import com.eCommerce_Backend_Project.Backend_Project.data.user.entity.User_Roles_Table;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRolesRepository extends CrudRepository<User_Roles_Table, Integer> {

}
