package com.job.careersite.serviceauth;

import java.util.Map;
import java.util.Optional;

import com.job.careersite.dto.RegisterRequest;
import com.job.careersite.usersentity.LoginPage;
import com.job.careersite.usersentity.User;

public interface AuthService {
    User registerUser(User registerRequest);
    User registerAdmin(User registerRequest);  
    User loginUser(LoginPage loginRequest);
    Map<String, Object> createAuthResponse(User user);
    boolean existsByEmail(String email); 
}