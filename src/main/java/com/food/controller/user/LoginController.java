package com.food.controller.user;

import com.food.model.User;
import com.food.model.dto.LoginRequest;
import com.food.model.dto.LoginResponse;
import com.food.repository.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Optional<User> user = userDao.findByEmail(request.getUserId());

        if (user.isEmpty()) {
            user = userDao.findByMobile(request.getUserId());
        }

        if (user.isPresent() &&
                user.get().getPassword().equals(request.getPassword())) {

            return new LoginResponse(true, "Login successful");
        }

        return new LoginResponse(false, "Invalid user or password");
    }
}
