package io.github.matheusplaza.fitlogtds.repository;

import io.github.matheusplaza.fitlogtds.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggedExerciseRepository extends JpaRepository<WorkoutSession,Integer> {

}