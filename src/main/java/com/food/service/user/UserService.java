package com.food.service.user;

import com.food.model.ApiResponse;
import com.food.model.User;
import com.food.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    ApiResponse<User> userRegistration(User user);

    ApiResponse<LoginResponse> loginUser(LoginRequest loginRequest);

    ApiResponse<User> changePassword(ChangePasswordRequest passwordRequest, String token);

    ApiResponse<LogoutResponse> logoutUser(String token);
}
