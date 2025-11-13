package io.github.matheusplaza.fitlogtds.repository;

import io.github.matheusplaza.fitlogtds.model.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Long> {
    List<WorkoutRoutine> findByNameLike(String name);

    @Query("SELECT DISTINCT r FROM WorkoutRoutine r " +
            "LEFT JOIN FETCH r.exercises " +
            "WHERE r.user.id = :userId")
    List<WorkoutRoutine> findAllWithExercises(@Param("userId") Long userId);
}
