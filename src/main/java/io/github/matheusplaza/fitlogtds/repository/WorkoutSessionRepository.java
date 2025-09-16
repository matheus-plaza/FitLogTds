package io.github.matheusplaza.fitlogtds.repository;

import io.github.matheusplaza.fitlogtds.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession,Long> {

    @Query("SELECT DISTINCT ws FROM WorkoutSession ws " +
            "LEFT JOIN FETCH ws.loggedExercises le " +
            "LEFT JOIN FETCH le.sets " +
            "ORDER BY ws.sessionDate DESC")
    List<WorkoutSession> findAllWithDetails();

    @Query("SELECT DISTINCT ws FROM WorkoutSession ws " +
            "LEFT JOIN FETCH ws.loggedExercises le " +
            "LEFT JOIN FETCH le.sets " +
            "WHERE ws.id = :id")
    Optional<WorkoutSession> findByIdWithDetails(Long id);
}
