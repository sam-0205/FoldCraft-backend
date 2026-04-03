package com.example.backend.repository;

import com.example.backend.entity.Origami;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrigamiRepository extends JpaRepository<Origami, Long> {

    Page<Origami> findByCategory(Origami.OrigamiCategory category, Pageable pageable);

    Page<Origami> findByDifficulty(Origami.DifficultyLevel difficulty, Pageable pageable);

    Page<Origami> findByCategoryAndDifficulty(
        Origami.OrigamiCategory category,
        Origami.DifficultyLevel difficulty,
        Pageable pageable
    );

    List<Origami> findByIsFeaturedTrue();

    @Query("SELECT o FROM Origami o WHERE " +
           "LOWER(o.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(o.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Origami> searchOrigamis(@Param("query") String query, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Origami o SET o.viewCount = o.viewCount + 1 WHERE o.id = :id")
    void incrementViewCount(@Param("id") Long id);

    Page<Origami> findAllByOrderByViewCountDesc(Pageable pageable);
    Page<Origami> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
