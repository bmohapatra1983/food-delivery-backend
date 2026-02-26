package com.food.controller.user;

import com.food.model.ApiResponse;
import com.food.model.User;
import com.food.model.dto.LoginRequest;
import com.food.model.dto.LoginResponse;
import com.food.model.dto.UserRegistrationRequest;
import com.food.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<ApiResponse<User>> userRegistration(@RequestBody User user){
        return new ResponseEntity<>(userService.userRegistration(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> userLogin(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.CREATED);
    }
}
