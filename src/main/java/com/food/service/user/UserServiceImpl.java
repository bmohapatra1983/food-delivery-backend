package com.food.service.user;

import com.food.model.ApiResponse;
import com.food.model.User;
import com.food.model.dto.LoginRequest;
import com.food.model.dto.LoginResponse;
import com.food.model.dto.UserRegistrationRequest;
import com.food.repository.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<User> userRegistration(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userDao.save(user);

        return ApiResponse.<User>builder()
                .status("success")
                .httpStatus(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .data(savedUser)
                .build();
    }

    @Override
    public ApiResponse<User> loginUser(LoginRequest loginRequest) {

        Optional<User> byEmailOrMobile = userDao.findByEmailOrMobile(loginRequest.getUserId(),loginRequest.getUserId());


        if (byEmailOrMobile.isPresent() &&
                byEmailOrMobile.get().getPassword().equals(loginRequest.getPassword())) {

            return ApiResponse.<User>builder()
                    .status("success")
                    .httpStatus(HttpStatus.OK.value())
                    .message("User Present")
                    .data(byEmailOrMobile.get())
                    .build();
        }
        return ApiResponse.<User>builder()
                .status("NOT_FOUND")
                .httpStatus(HttpStatus.NO_CONTENT.value())
                .message("User Does Not Exist!")
                .data(null)
                .build();
    }
}
