package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDto {

    // ================= REGISTER REQUEST =================
    public static class RegisterRequest {

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        private String username;

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        private String displayName;

        public RegisterRequest() {}

        public RegisterRequest(String username, String email, String password, String displayName) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.displayName = displayName;
        }

        // Getters & Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }
    }

    // ================= LOGIN REQUEST =================
    public static class LoginRequest {

        @NotBlank(message = "Username or email is required")
        private String usernameOrEmail;

        @NotBlank(message = "Password is required")
        private String password;

        public LoginRequest() {}

        public LoginRequest(String usernameOrEmail, String password) {
            this.usernameOrEmail = usernameOrEmail;
            this.password = password;
        }

        public String getUsernameOrEmail() { return usernameOrEmail; }
        public void setUsernameOrEmail(String usernameOrEmail) { this.usernameOrEmail = usernameOrEmail; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // ================= AUTH RESPONSE =================
    public static class AuthResponse {

        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private UserDto user;

        public AuthResponse() {}

        public AuthResponse(String accessToken, String refreshToken, String tokenType, UserDto user) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.tokenType = tokenType;
            this.user = user;
        }

        public static AuthResponse of(String accessToken, String refreshToken, UserDto user) {
            return new AuthResponse(accessToken, refreshToken, "Bearer", user);
        }

        public String getAccessToken() { return accessToken; }
        public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

        public String getRefreshToken() { return refreshToken; }
        public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

        public String getTokenType() { return tokenType; }
        public void setTokenType(String tokenType) { this.tokenType = tokenType; }

        public UserDto getUser() { return user; }
        public void setUser(UserDto user) { this.user = user; }
    }

    // ================= REFRESH REQUEST =================
    public static class RefreshRequest {

        @NotBlank
        private String refreshToken;

        public RefreshRequest() {}

        public RefreshRequest(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getRefreshToken() { return refreshToken; }
        public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    }
}