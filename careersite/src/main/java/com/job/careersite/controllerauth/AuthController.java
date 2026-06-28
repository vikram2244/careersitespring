package com.job.careersite.controllerauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.job.careersite.serviceauth.AuthService;
import com.job.careersite.usersentity.LoginPage;
import com.job.careersite.usersentity.User;
import com.job.careersite.usersrepo.RegisterRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173", "https://shimmering-liger-862c4b.netlify.app"}, allowCredentials = "true")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User registerRequest) {
        try {
            User savedUser = authService.registerUser(registerRequest);
            Map<String, Object> response = authService.createAuthResponse(savedUser);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("detail", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("detail", "An error occurred during registration"));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginPage loginRequest) {
        try {
            User user = authService.loginUser(loginRequest);
            Map<String, Object> response = authService.createAuthResponse(user);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("detail", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("detail", "An error occurred during login"));
        }
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        return ResponseEntity.ok(authHeader);
    }
    
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody User registerRequest) {
        try {
            if (authService.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest().body(Map.of("detail", "Email already registered"));
            }
            User savedUser = authService.registerAdmin(registerRequest);
            Map<String, Object> response = authService.createAuthResponse(savedUser);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("detail", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("detail", "An error occurred during registration"));
        }
    }
    @GetMapping("/hello")
public ResponseEntity<String> hello() {
    return ResponseEntity.ok("Hello World !! Wake up rey!!");
}
//    @GetMapping("/check-user/{email}")
//    public ResponseEntity<?> checkUser(@PathVariable String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        if (user.isPresent()) {
//            Map<String, Object> info = new HashMap<>();
//            info.put("exists", true);
//            info.put("email", user.get().getEmail());
//            info.put("role", user.get().getRole());
//            info.put("passwordHash", user.get().getPassword());
//            return ResponseEntity.ok(info);
//        }
//        return ResponseEntity.ok(Map.of("exists", false));
//    }
    
}
