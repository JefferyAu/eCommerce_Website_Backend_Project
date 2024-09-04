package com.eCommerce_Backend_Project.Backend_Project.api;


import com.eCommerce_Backend_Project.Backend_Project.config.EnvConfig;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.response.*;
import com.eCommerce_Backend_Project.Backend_Project.service.UserService;
import com.eCommerce_Backend_Project.Backend_Project.util.JwtUtil;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin({EnvConfig.DEV_BASE_URL, EnvConfig.PRO_BASE_RUL})
public class UserApi {

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me/details")
    public UserResponseDto getMyUserDetails(JwtAuthenticationToken jwtToken) {
        UserResponseDto loginUser = new UserResponseDto(jwtToken);
        return loginUser;
    }

    @GetMapping("/userrole")
    public Boolean getUserRoleStatus(JwtAuthenticationToken jwtToken){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwtToken);
        Boolean isAdmin = userService.getUserRoleStatus(firebaseUserData);
        return isAdmin;
    }
}