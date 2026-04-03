package com.example.backend.dto;

import com.example.backend.entity.Origami;

import java.time.LocalDateTime;
import java.util.List;

public class OrigamiDto {

    private Long id;
    private String title;
    private String description;
    private Origami.DifficultyLevel difficulty;
    private Origami.OrigamiCategory category;
    private String imageUrl;
    private String thumbnailUrl;
    private String youtubeUrl;
    private String youtubeEmbedId;
    private Integer stepsCount;
    private Integer estimatedTimeMinutes;
    private List<String> tags;
    private List<StepDto> steps;
    private String paperSize;
    private String paperType;
    private Boolean isFeatured;
    private Long viewCount;
    private LocalDateTime createdAt;
    private Boolean isFavorited;
    private Boolean isCompleted;

    // ================= CONSTRUCTORS =================
    public OrigamiDto() {}

    public OrigamiDto(Long id, String title, String description,
                      Origami.DifficultyLevel difficulty,
                      Origami.OrigamiCategory category,
                      String imageUrl, String thumbnailUrl,
                      String youtubeUrl, String youtubeEmbedId,
                      Integer stepsCount, Integer estimatedTimeMinutes,
                      List<String> tags, List<StepDto> steps,
                      String paperSize, String paperType,
                      Boolean isFeatured, Long viewCount,
                      LocalDateTime createdAt,
                      Boolean isFavorited, Boolean isCompleted) {

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
        this.isFavorited = isFavorited;
        this.isCompleted = isCompleted;
    }

    // ================= GETTERS & SETTERS =================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Origami.DifficultyLevel getDifficulty() { return difficulty; }
    public void setDifficulty(Origami.DifficultyLevel difficulty) { this.difficulty = difficulty; }

    public Origami.OrigamiCategory getCategory() { return category; }
    public void setCategory(Origami.OrigamiCategory category) { this.category = category; }

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

    public List<StepDto> getSteps() { return steps; }
    public void setSteps(List<StepDto> steps) { this.steps = steps; }

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

    public Boolean getIsFavorited() { return isFavorited; }
    public void setIsFavorited(Boolean isFavorited) { this.isFavorited = isFavorited; }

    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    // ================= INNER CLASS: STEP DTO =================
    public static class StepDto {

        private String title;
        private String description;
        private String imageUrl;

        public StepDto() {}

        public StepDto(String title, String description, String imageUrl) {
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

    // ================= INNER CLASS: CREATE REQUEST =================
    public static class CreateRequest {

        private String title;
        private String description;
        private Origami.DifficultyLevel difficulty;
        private Origami.OrigamiCategory category;
        private String imageUrl;
        private String thumbnailUrl;
        private String youtubeUrl;
        private String youtubeEmbedId;
        private Integer stepsCount;
        private Integer estimatedTimeMinutes;
        private List<String> tags;
        private List<StepDto> steps;
        private String paperSize;
        private String paperType;
        private Boolean isFeatured;

        public CreateRequest() {}

        public CreateRequest(String title, String description,
                             Origami.DifficultyLevel difficulty,
                             Origami.OrigamiCategory category,
                             String imageUrl, String thumbnailUrl,
                             String youtubeUrl, String youtubeEmbedId,
                             Integer stepsCount, Integer estimatedTimeMinutes,
                             List<String> tags, List<StepDto> steps,
                             String paperSize, String paperType,
                             Boolean isFeatured) {

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
        }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Origami.DifficultyLevel getDifficulty() { return difficulty; }
        public void setDifficulty(Origami.DifficultyLevel difficulty) { this.difficulty = difficulty; }

        public Origami.OrigamiCategory getCategory() { return category; }
        public void setCategory(Origami.OrigamiCategory category) { this.category = category; }

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

        public List<StepDto> getSteps() { return steps; }
        public void setSteps(List<StepDto> steps) { this.steps = steps; }

        public String getPaperSize() { return paperSize; }
        public void setPaperSize(String paperSize) { this.paperSize = paperSize; }

        public String getPaperType() { return paperType; }
        public void setPaperType(String paperType) { this.paperType = paperType; }

        public Boolean getIsFeatured() { return isFeatured; }
        public void setIsFeatured(Boolean isFeatured) { this.isFeatured = isFeatured; }
    }

    // ================= MAPPER =================
    public static OrigamiDto from(Origami origami) {
        return from(origami, false, false);
    }

    public static OrigamiDto from(Origami origami, boolean isFavorited, boolean isCompleted) {

        List<StepDto> stepDtos = null;

        if (origami.getSteps() != null) {
            stepDtos = origami.getSteps().stream()
                    .map(s -> new StepDto(
                            s.getTitle(),
                            s.getDescription(),
                            s.getImageUrl()
                    ))
                    .toList();
        }

        return new OrigamiDto(
                origami.getId(),
                origami.getTitle(),
                origami.getDescription(),
                origami.getDifficulty(),
                origami.getCategory(),
                origami.getImageUrl(),
                origami.getThumbnailUrl(),
                origami.getYoutubeUrl(),
                origami.getYoutubeEmbedId(),
                origami.getStepsCount(),
                origami.getEstimatedTimeMinutes(),
                origami.getTags(),
                stepDtos,
                origami.getPaperSize(),
                origami.getPaperType(),
                origami.getIsFeatured(),
                origami.getViewCount(),
                origami.getCreatedAt(),
                isFavorited,
                isCompleted
        );
    }
}