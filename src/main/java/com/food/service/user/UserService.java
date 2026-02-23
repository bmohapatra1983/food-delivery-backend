package com.food.service.user;

import com.food.model.ApiResponse;
import com.food.model.User;
import com.food.model.dto.UserRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ApiResponse<User> userRegistration(User user);
}
