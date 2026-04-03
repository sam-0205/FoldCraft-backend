package com.example.backend.controller;

import com.example.backend.dto.OrigamiDto;
import com.example.backend.dto.UserDto;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.OrigamiService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final OrigamiService origamiService;

    // ✅ MANUAL CONSTRUCTOR
    public UserController(UserRepository userRepository,
                          OrigamiService origamiService) {
        this.userRepository = userRepository;
        this.origamiService = origamiService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return ResponseEntity.ok(UserDto.from(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateProfileRequest request
    ) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (request.getDisplayName() != null) user.setDisplayName(request.getDisplayName());
        if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());

        userRepository.save(user);

        return ResponseEntity.ok(UserDto.from(user));
    }

    @GetMapping("/me/favorites")
    public ResponseEntity<List<OrigamiDto>> getFavorites(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(
                origamiService.getUserFavorites(userDetails.getUsername())
        );
    }

    @GetMapping("/me/completed")
    public ResponseEntity<List<OrigamiDto>> getCompleted(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(
                origamiService.getUserCompleted(userDetails.getUsername())
        );
    }

    // ✅ RECORD (no change needed)
    record UpdateProfileRequest(String displayName, String avatarUrl) {
        public String getDisplayName() { return displayName; }
        public String getAvatarUrl() { return avatarUrl; }
    }
}