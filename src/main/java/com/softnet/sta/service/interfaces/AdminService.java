package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.response.LoginResponse;
import com.softnet.sta.enums.UserRole;

import java.util.List;

public interface AdminService {
    List<LoginResponse> getAllUsers();

    LoginResponse getUserById(Long id);

    String deleteUser(Long id);

    List<?> getUserByUserRole(UserRole userRole);
}
