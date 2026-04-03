package com.example.backend.controller;

import com.example.backend.dto.OrigamiDto;
import com.example.backend.entity.Origami;
import com.example.backend.service.OrigamiService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/origamis")
public class OrigamiController {

    private final OrigamiService origamiService;

    // ✅ MANUAL CONSTRUCTOR
    public OrigamiController(OrigamiService origamiService) {
        this.origamiService = origamiService;
    }

    @GetMapping
    public ResponseEntity<Page<OrigamiDto>> getAllOrigamis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails != null ? userDetails.getUsername() : null;

        return ResponseEntity.ok(
                origamiService.getAllOrigamis(page, size, sort, category, difficulty, username)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrigamiDto> getOrigamiById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails != null ? userDetails.getUsername() : null;

        return ResponseEntity.ok(
                origamiService.getOrigamiById(id, username)
        );
    }

    @GetMapping("/featured")
    public ResponseEntity<List<OrigamiDto>> getFeaturedOrigamis() {
        return ResponseEntity.ok(origamiService.getFeaturedOrigamis());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrigamiDto>> searchOrigamis(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return ResponseEntity.ok(origamiService.searchOrigamis(q, page, size));
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getCategories() {
        return ResponseEntity.ok(Map.of(
                "categories", Arrays.stream(Origami.OrigamiCategory.values()).map(Enum::name).toList(),
                "difficulties", Arrays.stream(Origami.DifficultyLevel.values()).map(Enum::name).toList()
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrigamiDto> createOrigami(
            @RequestBody OrigamiDto.CreateRequest request) {

        return ResponseEntity.ok(origamiService.createOrigami(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrigamiDto> updateOrigami(
            @PathVariable Long id,
            @RequestBody OrigamiDto.CreateRequest request
    ) {
        return ResponseEntity.ok(origamiService.updateOrigami(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrigami(@PathVariable Long id) {
        origamiService.deleteOrigami(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/favorite")
    public ResponseEntity<Void> toggleFavorite(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        origamiService.toggleFavorite(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> toggleCompleted(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        origamiService.toggleCompleted(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}