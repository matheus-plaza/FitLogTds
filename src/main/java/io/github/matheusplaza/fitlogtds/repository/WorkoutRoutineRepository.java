package io.github.matheusplaza.fitlogtds.repository;

import io.github.matheusplaza.fitlogtds.model.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Long> {
    List<WorkoutRoutine> findByNameLike(String name);

    @Query("SELECT DISTINCT r FROM WorkoutRoutine r LEFT JOIN FETCH r.exercises")
    List<WorkoutRoutine> findAllWithExercises();
}
