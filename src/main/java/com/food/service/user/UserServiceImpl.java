package com.food.service.user;

import com.food.model.ApiResponse;
import com.food.model.User;
import com.food.model.dto.*;
import com.food.model.dto.KitchenCategory.KitchenCategoryMaster;
import com.food.model.dto.KitchenCategory.KitchenStoreInformation;
import com.food.repository.user.UserDao;
import com.food.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

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
    public ApiResponse<LoginResponse> loginUser(LoginRequest loginRequest) {

        Optional<User> byEmailOrMobile = userDao.findByEmailOrMobile(loginRequest.getUserId(),loginRequest.getUserId());

        if (byEmailOrMobile.isPresent() &&
                passwordEncoder.matches(
                        loginRequest.getPassword(),           // raw password
                        byEmailOrMobile.get().getPassword()   // encoded password from DB
                )) {

            User user = byEmailOrMobile.get();

            // Generate JWT token
            String token = jwtTokenProvider.generateToken(user.getEmail(), user.getId(), user.getUserType());
            String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail(), user.getId());

            LoginResponse loginResponse = LoginResponse.builder()
                    .success(true)
                    .message("Login successful")
                    .token(token)
                    .refreshToken(refreshToken)
                    .userId(user.getId())
                    .email(user.getEmail())
                    .userType(user.getUserType())
                    .expiresIn(jwtExpiration / 1000) // Convert to seconds
                    .build();

            return ApiResponse.<LoginResponse>builder()
                    .status("success")
                    .httpStatus(HttpStatus.OK.value())
                    .message("User logged in successfully")
                    .data(loginResponse)
                    .build();
        }

        return ApiResponse.<LoginResponse>builder()
                .status("UNAUTHORIZED")
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .message("Invalid email/mobile or password")
                .data(null)
                .build();
    }

    @Override
    public ApiResponse<User> changePassword(ChangePasswordRequest passwordRequest, String token) {
        // Validate token
        if (token == null || token.isEmpty() || !jwtTokenProvider.validateToken(token)) {
            return ApiResponse.<User>builder()
                    .status("unauthorized")
                    .httpStatus(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid or expired token. Please login again.")
                    .data(null)
                    .build();
        }

        // Check if token is blacklisted (logged out)
        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            return ApiResponse.<User>builder()
                    .status("unauthorized")
                    .httpStatus(HttpStatus.UNAUTHORIZED.value())
                    .message("Token has been revoked. Please login again.")
                    .data(null)
                    .build();
        }

        // Extract email from token
        String tokenEmail = jwtTokenProvider.getEmailFromToken(token);

        // Verify user is changing their own password (case-insensitive comparison)
        if (!tokenEmail.equalsIgnoreCase(passwordRequest.getMailId())) {
            return ApiResponse.<User>builder()
                    .status("forbidden")
                    .httpStatus(HttpStatus.FORBIDDEN.value())
                    .message("You can only change your own password.")
                    .data(null)
                    .build();
        }

        Optional<User> userData = userDao.findByEmail(passwordRequest.getMailId());

        if(userData.isPresent()){
            User user = userData.get();

            // Verify old password matches
            if (!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
                return ApiResponse.<User>builder()
                        .status("failure")
                        .httpStatus(HttpStatus.UNAUTHORIZED.value())
                        .message("Current password is incorrect!")
                        .data(null)
                        .build();
            }

            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userDao.save(user);
            return ApiResponse.<User>builder()
                    .status("success")
                    .httpStatus(HttpStatus.OK.value())
                    .message("Password Updated Successfully!")
                    .data(null)
                    .build();
        }
        else {
            return ApiResponse.<User>builder()
                    .status("failure")
                    .httpStatus(HttpStatus.NO_CONTENT.value())
                    .message("User Id Not Found!")
                    .data(null)
                    .build();
        }
    }

    @Override
    public ApiResponse<LogoutResponse> logoutUser(String token) {
        try {
            // Validate token before blacklisting
            if (token == null || token.isEmpty() || !jwtTokenProvider.validateToken(token)) {
                return ApiResponse.<LogoutResponse>builder()
                        .status("failure")
                        .httpStatus(HttpStatus.UNAUTHORIZED.value())
                        .message("Invalid or expired token")
                        .data(null)
                        .build();
            }

            // Extract user info from token
            Long userId = jwtTokenProvider.getUserIdFromToken(token);

            // Blacklist the token
            tokenBlacklistService.blacklistToken(token);

            // Build logout response
            LogoutResponse logoutResponse = LogoutResponse.builder()
                    .success(true)
                    .message("Logout successful")
                    .userId(userId)
                    .build();

            return ApiResponse.<LogoutResponse>builder()
                    .status("success")
                    .httpStatus(HttpStatus.OK.value())
                    .message("User logged out successfully")
                    .data(logoutResponse)
                    .build();

        } catch (Exception e) {
            return ApiResponse.<LogoutResponse>builder()
                    .status("error")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Logout failed: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @Override
    public ApiResponse<KitchenStoreInformation> saveKitchenStore(KitchenStoreInformation storeInfo) {
        return ApiResponse.<KitchenStoreInformation>builder()
                .status("success")
                .httpStatus(HttpStatus.CREATED.value())
                .message("Kitchen store saved successfully")
                .data(storeInfo)
                .build();
    }

    @Override
    public ApiResponse<KitchenCategoryMaster> saveKitchenCategory(KitchenCategoryMaster categoryInfo) {
        return ApiResponse.<KitchenCategoryMaster>builder()
                .status("success")
                .httpStatus(HttpStatus.CREATED.value())
                .message("Kitchen category saved successfully")
                .data(categoryInfo)
                .build();
    }
}
