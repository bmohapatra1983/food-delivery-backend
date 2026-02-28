package com.food.service.user;

import com.food.model.ApiResponse;
import com.food.model.User;
import com.food.model.dto.ChangePasswordRequest;
import com.food.model.dto.LoginRequest;
import com.food.model.dto.UserRegistrationRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    ApiResponse<User> userRegistration(User user);

    ApiResponse<User> loginUser(LoginRequest loginRequest);

    ApiResponse<User> changePassword(ChangePasswordRequest passwordRequest);
}
