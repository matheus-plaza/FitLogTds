package io.github.matheusplaza.fitlogtds.repository;

import io.github.matheusplaza.fitlogtds.model.LoggedExercise;
import io.github.matheusplaza.fitlogtds.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession,Long> {

    // Query 1: Busca as sessões e o PRIMEIRO nível de filhos (loggedExercises)
    @Query("SELECT DISTINCT ws FROM WorkoutSession ws LEFT JOIN FETCH ws.loggedExercises WHERE ws.id = :id")
    Optional<WorkoutSession> findByIdWithExercises(Long id);

    // Query 2: Busca os LoggedExercises (já com a sessão) e o SEGUNDO nível de filhos (sets)
    @Query("SELECT DISTINCT le FROM LoggedExercise le LEFT JOIN FETCH le.sets WHERE le.session.id = :sessionId")
    List<LoggedExercise> findLoggedExercisesWithSetsBySessionId(Long sessionId);

    // Query para a lista principal
    @Query("SELECT DISTINCT ws FROM WorkoutSession ws LEFT JOIN FETCH ws.loggedExercises")
    List<WorkoutSession> findAllWithExercises();

    @Query("SELECT DISTINCT le FROM LoggedExercise le LEFT JOIN FETCH le.sets WHERE le IN :loggedExercises")
    List<LoggedExercise> findLoggedExercisesWithSets(List<LoggedExercise> loggedExercises);
}
