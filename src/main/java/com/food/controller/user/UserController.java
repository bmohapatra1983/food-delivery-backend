package com.food.controller.user;

import com.food.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/registration")
    public String userRegistration(@RequestBody User user){
        return "Dummy return ..";
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody User user){
        return "Login Success";
    }
}
