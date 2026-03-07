package com.food.service.user;

import com.food.model.ApiResponse;
import com.food.model.User;
import com.food.model.dto.UserRegistrationRequest;
import com.food.repository.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao dao;

    @Override
    public ApiResponse<User> userRegistration(User user) {
        User savedUser = dao.save(user);

        ApiResponse<User> response = ApiResponse.<User>builder()
                .status("success")
                .httpStatus(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .data(savedUser)
                .build();
        return response;
    }
}
