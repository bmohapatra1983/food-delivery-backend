package com.food.service.user;

import com.food.model.ApiResponse;
import com.food.model.dto.KitchenCategory.KitchenCategoryMaster;
import com.food.model.dto.KitchenCategory.KitchenStoreInformation;
import com.food.model.User;
import com.food.model.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ApiResponse<User> userRegistration(User user);

    ApiResponse<LoginResponse> loginUser(LoginRequest loginRequest);

    ApiResponse<User> changePassword(ChangePasswordRequest passwordRequest, String token);

    ApiResponse<LogoutResponse> logoutUser(String token);

    ApiResponse<KitchenStoreInformation> saveKitchenStore(KitchenStoreInformation storeInfo);

    ApiResponse<KitchenCategoryMaster> saveKitchenCategory(KitchenCategoryMaster categoryInfo);
}
