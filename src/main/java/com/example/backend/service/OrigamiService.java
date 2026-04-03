package com.example.backend.service;

import com.example.backend.dto.OrigamiDto;
import com.example.backend.entity.Origami;
import com.example.backend.entity.User;
import com.example.backend.repository.OrigamiRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrigamiService {

    private final OrigamiRepository origamiRepository;
    private final UserRepository userRepository;

    // ✅ MANUAL CONSTRUCTOR
    public OrigamiService(OrigamiRepository origamiRepository,
                          UserRepository userRepository) {
        this.origamiRepository = origamiRepository;
        this.userRepository = userRepository;
    }

    // ================= GET ALL =================
    public Page<OrigamiDto> getAllOrigamis(int page, int size, String sort,
                                           String category, String difficulty,
                                           String username) {

        Pageable pageable = buildPageable(page, size, sort);
        Optional<User> currentUser = username != null
                ? userRepository.findByUsername(username)
                : Optional.empty();

        Page<Origami> origamiPage;

        if (category != null && difficulty != null) {
            origamiPage = origamiRepository.findByCategoryAndDifficulty(
                    Origami.OrigamiCategory.valueOf(category.toUpperCase()),
                    Origami.DifficultyLevel.valueOf(difficulty.toUpperCase()),
                    pageable
            );
        } else if (category != null) {
            origamiPage = origamiRepository.findByCategory(
                    Origami.OrigamiCategory.valueOf(category.toUpperCase()),
                    pageable
            );
        } else if (difficulty != null) {
            origamiPage = origamiRepository.findByDifficulty(
                    Origami.DifficultyLevel.valueOf(difficulty.toUpperCase()),
                    pageable
            );
        } else {
            origamiPage = origamiRepository.findAll(pageable);
        }

        return origamiPage.map(o -> toDto(o, currentUser));
    }

    // ================= GET BY ID =================
    public OrigamiDto getOrigamiById(Long id, String username) {

        Origami origami = origamiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Origami not found with id: " + id));

        origamiRepository.incrementViewCount(id);

        Optional<User> currentUser = username != null
                ? userRepository.findByUsername(username)
                : Optional.empty();

        return toDto(origami, currentUser);
    }

    // ================= FEATURED =================
    public List<OrigamiDto> getFeaturedOrigamis() {
        return origamiRepository.findByIsFeaturedTrue()
                .stream()
                .map(OrigamiDto::from)
                .toList();
    }

    // ================= SEARCH =================
    public Page<OrigamiDto> searchOrigamis(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return origamiRepository.searchOrigamis(query, pageable)
                .map(OrigamiDto::from);
    }

    // ================= CREATE =================
    @Transactional
    public OrigamiDto createOrigami(OrigamiDto.CreateRequest request) {

        List<Origami.OrigamiStep> steps = null;

        if (request.getSteps() != null) {
            steps = request.getSteps().stream()
                    .map(s -> new Origami.OrigamiStep(
                            s.getTitle(),
                            s.getDescription(),
                            s.getImageUrl()
                    ))
                    .toList();
        }

        // ❌ builder removed
        Origami origami = new Origami();

        origami.setTitle(request.getTitle());
        origami.setDescription(request.getDescription());
        origami.setDifficulty(request.getDifficulty());
        origami.setCategory(request.getCategory());
        origami.setImageUrl(request.getImageUrl());
        origami.setThumbnailUrl(request.getThumbnailUrl());
        origami.setYoutubeUrl(request.getYoutubeUrl());
        origami.setYoutubeEmbedId(request.getYoutubeEmbedId());
        origami.setStepsCount(request.getStepsCount());
        origami.setEstimatedTimeMinutes(request.getEstimatedTimeMinutes());
        origami.setTags(request.getTags());
        origami.setSteps(steps);
        origami.setPaperSize(request.getPaperSize());
        origami.setPaperType(request.getPaperType());
        origami.setIsFeatured(
                request.getIsFeatured() != null && request.getIsFeatured()
        );

        return OrigamiDto.from(origamiRepository.save(origami));
    }

    // ================= UPDATE =================
    @Transactional
    public OrigamiDto updateOrigami(Long id, OrigamiDto.CreateRequest request) {

        Origami origami = origamiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Origami not found"));

        if (request.getTitle() != null) origami.setTitle(request.getTitle());
        if (request.getDescription() != null) origami.setDescription(request.getDescription());
        if (request.getDifficulty() != null) origami.setDifficulty(request.getDifficulty());
        if (request.getCategory() != null) origami.setCategory(request.getCategory());
        if (request.getImageUrl() != null) origami.setImageUrl(request.getImageUrl());
        if (request.getThumbnailUrl() != null) origami.setThumbnailUrl(request.getThumbnailUrl());
        if (request.getYoutubeUrl() != null) origami.setYoutubeUrl(request.getYoutubeUrl());
        if (request.getYoutubeEmbedId() != null) origami.setYoutubeEmbedId(request.getYoutubeEmbedId());
        if (request.getStepsCount() != null) origami.setStepsCount(request.getStepsCount());
        if (request.getEstimatedTimeMinutes() != null) origami.setEstimatedTimeMinutes(request.getEstimatedTimeMinutes());
        if (request.getTags() != null) origami.setTags(request.getTags());
        if (request.getPaperSize() != null) origami.setPaperSize(request.getPaperSize());
        if (request.getPaperType() != null) origami.setPaperType(request.getPaperType());
        if (request.getIsFeatured() != null) origami.setIsFeatured(request.getIsFeatured());

        return OrigamiDto.from(origamiRepository.save(origami));
    }

    // ================= DELETE =================
    @Transactional
    public void deleteOrigami(Long id) {
        origamiRepository.deleteById(id);
    }

    // ================= FAVORITE =================
    @Transactional
    public void toggleFavorite(Long origamiId, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Origami origami = origamiRepository.findById(origamiId)
                .orElseThrow(() -> new RuntimeException("Origami not found"));

        if (user.getFavorites().contains(origami)) {
            user.getFavorites().remove(origami);
        } else {
            user.getFavorites().add(origami);
        }

        userRepository.save(user);
    }

    // ================= COMPLETED =================
    @Transactional
    public void toggleCompleted(Long origamiId, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Origami origami = origamiRepository.findById(origamiId)
                .orElseThrow(() -> new RuntimeException("Origami not found"));

        if (user.getCompleted().contains(origami)) {
            user.getCompleted().remove(origami);
        } else {
            user.getCompleted().add(origami);
        }

        userRepository.save(user);
    }

    // ================= USER FAVORITES =================
    public List<OrigamiDto> getUserFavorites(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user.getFavorites().stream()
                .map(o -> toDto(o, Optional.of(user)))
                .toList();
    }

    // ================= USER COMPLETED =================
    public List<OrigamiDto> getUserCompleted(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user.getCompleted().stream()
                .map(o -> toDto(o, Optional.of(user)))
                .toList();
    }

    // ================= HELPER =================
    private OrigamiDto toDto(Origami origami, Optional<User> user) {

        boolean isFav = user.map(u ->
                u.getFavorites() != null && u.getFavorites().contains(origami)
        ).orElse(false);

        boolean isDone = user.map(u ->
                u.getCompleted() != null && u.getCompleted().contains(origami)
        ).orElse(false);

        return OrigamiDto.from(origami, isFav, isDone);
    }

    private Pageable buildPageable(int page, int size, String sort) {

        Sort s = switch (sort != null ? sort : "newest") {
            case "popular" -> Sort.by(Sort.Direction.DESC, "viewCount");
            case "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };

        return PageRequest.of(page, size, s);
    }
}