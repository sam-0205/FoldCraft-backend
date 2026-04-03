package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "origamis")
public class Origami {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrigamiCategory category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "youtube_url")
    private String youtubeUrl;

    @Column(name = "youtube_embed_id")
    private String youtubeEmbedId;

    @Column(name = "steps_count")
    private Integer stepsCount;

    @Column(name = "estimated_time_minutes")
    private Integer estimatedTimeMinutes;

    @ElementCollection
    @CollectionTable(name = "origami_tags", joinColumns = @JoinColumn(name = "origami_id"))
    @Column(name = "tag")
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "origami_steps", joinColumns = @JoinColumn(name = "origami_id"))
    @OrderColumn(name = "step_order")
    private List<OrigamiStep> steps;

    @Column(name = "paper_size")
    private String paperSize;

    @Column(name = "paper_type")
    private String paperType;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ================= CONSTRUCTORS =================
    public Origami() {}

    public Origami(Long id, String title, String description,
                   DifficultyLevel difficulty, OrigamiCategory category,
                   String imageUrl, String thumbnailUrl, String youtubeUrl,
                   String youtubeEmbedId, Integer stepsCount,
                   Integer estimatedTimeMinutes, List<String> tags,
                   List<OrigamiStep> steps, String paperSize,
                   String paperType, Boolean isFeatured,
                   Long viewCount, LocalDateTime createdAt,
                   LocalDateTime updatedAt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.category = category;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.youtubeUrl = youtubeUrl;
        this.youtubeEmbedId = youtubeEmbedId;
        this.stepsCount = stepsCount;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.tags = tags;
        this.steps = steps;
        this.paperSize = paperSize;
        this.paperType = paperType;
        this.isFeatured = isFeatured;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

// ================= GETTERS & SETTERS =================
public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public DifficultyLevel getDifficulty() { return difficulty; }
    public void setDifficulty(DifficultyLevel difficulty) { this.difficulty = difficulty; }

    public OrigamiCategory getCategory() { return category; }
    public void setCategory(OrigamiCategory category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getYoutubeUrl() { return youtubeUrl; }
    public void setYoutubeUrl(String youtubeUrl) { this.youtubeUrl = youtubeUrl; }

    public String getYoutubeEmbedId() { return youtubeEmbedId; }
    public void setYoutubeEmbedId(String youtubeEmbedId) { this.youtubeEmbedId = youtubeEmbedId; }

    public Integer getStepsCount() { return stepsCount; }
    public void setStepsCount(Integer stepsCount) { this.stepsCount = stepsCount; }

    public Integer getEstimatedTimeMinutes() { return estimatedTimeMinutes; }
    public void setEstimatedTimeMinutes(Integer estimatedTimeMinutes) { this.estimatedTimeMinutes = estimatedTimeMinutes; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public List<OrigamiStep> getSteps() { return steps; }
    public void setSteps(List<OrigamiStep> steps) { this.steps = steps; }

    public String getPaperSize() { return paperSize; }
    public void setPaperSize(String paperSize) { this.paperSize = paperSize; }

    public String getPaperType() { return paperType; }
    public void setPaperType(String paperType) { this.paperType = paperType; }

    public Boolean getIsFeatured() { return isFeatured; }
    public void setIsFeatured(Boolean isFeatured) { this.isFeatured = isFeatured; }

    public Long getViewCount() { return viewCount; }
    public void setViewCount(Long viewCount) { this.viewCount = viewCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ================= JPA HOOKS =================
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (viewCount == null) viewCount = 0L;
        if (isFeatured == null) isFeatured = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ================= ENUMS =================
    public enum DifficultyLevel {
        BEGINNER, EASY, INTERMEDIATE, ADVANCED, EXPERT
    }

    public enum OrigamiCategory {
        ANIMALS, BIRDS, FLOWERS, GEOMETRIC, MODULAR,
        TRADITIONAL, SEASONAL, VEHICLES, PEOPLE, FANTASY
    }

    // ================= EMBEDDABLE =================
    @Embeddable
    public static class OrigamiStep {

        @Column(name = "step_title")
        private String title;

        @Column(name = "step_description", columnDefinition = "TEXT")
        private String description;

        @Column(name = "step_image_url")
        private String imageUrl;

        public OrigamiStep() {}

        public OrigamiStep(String title, String description, String imageUrl) {
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
        }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    }
}