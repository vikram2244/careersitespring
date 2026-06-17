package com.job.careersite.serviceauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.job.careersite.usersentity.LoginPage;
import com.job.careersite.usersentity.User;
import com.job.careersite.usersrepo.RegisterRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private RegisterRepo userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public User registerUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Register request cannot be null");
        }
        
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setName(user.getName());
        newUser.setRole("USER");
        return userRepository.save(newUser);
    }
    
    @Override
    public Map<String, Object> createAuthResponse(User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("role", user.getRole());
        response.put("token", "mock-jwt-token-" + System.currentTimeMillis());
        return response;
    }
    
    @Override
    public User loginUser(LoginPage loginRequest) {
        if (loginRequest == null) {
            throw new IllegalArgumentException("Login request cannot be null");
        }
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        
        System.out.println("Attempting login for email: " + email);
        System.out.println("Provided password length: " + (password != null ? password.length() : 0));
        
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isEmpty()) {
            System.out.println("User not found for email: " + email);
            throw new IllegalArgumentException("Invalid credentials");
        }
        User user = userOptional.get();
        
        System.out.println("User found: " + user.getEmail());
        System.out.println("Stored role: " + user.getRole());
        System.out.println("Stored password hash: " + user.getPassword());
        
        // Check password
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Password matches: " + passwordMatches);
        
        if (!passwordMatches) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        
        return user;
    }
    
   
    
    @Override
    public User registerAdmin(User registerRequest) {
        if (registerRequest == null) {
            throw new IllegalArgumentException("Register request cannot be null");
        }
        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (registerRequest.getName() == null || registerRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setName(registerRequest.getName());
        user.setRole("admin");  // Set role as admin
        
        return userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}