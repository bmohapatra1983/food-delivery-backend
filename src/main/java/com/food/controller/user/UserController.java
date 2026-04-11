package com.food.controller.user;

import com.food.model.ApiResponse;
import com.food.model.dto.KitchenCategory.KitchenCategoryMaster;
import com.food.model.dto.KitchenCategory.KitchenStoreInformation;
import com.food.model.User;
import com.food.model.dto.*;
import com.food.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<ApiResponse<LoginResponse>> userLogin(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ApiResponse<User>> changePassword(@RequestBody ChangePasswordRequest passwordRequest, @RequestHeader("Authorization") String authHeader){
        // Extract token from Bearer <token>
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        return new ResponseEntity<>(userService.changePassword(passwordRequest, token), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<LogoutResponse>> logoutUser(@RequestHeader("Authorization") String authHeader){
        // Extract token from Bearer <token>
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        return new ResponseEntity<>(userService.logoutUser(token), HttpStatus.OK);
    }

    @PostMapping("/kitchenStore")
    public ResponseEntity<ApiResponse<KitchenStoreInformation>> saveKitchenStore(
            @RequestBody KitchenStoreInformation storeInfo,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        return new ResponseEntity<>(
                userService.saveKitchenStore(storeInfo), HttpStatus.CREATED);
    }

    @PostMapping("/kitchenCategory")
    public ResponseEntity<ApiResponse<KitchenCategoryMaster>> saveKitchenCategory(
            @RequestBody KitchenCategoryMaster categoryInfo,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        return new ResponseEntity<>(
                userService.saveKitchenCategory(categoryInfo), HttpStatus.CREATED);
    }
}
