package io.github.matheusplaza.fitlogtds.repository;

import io.github.matheusplaza.fitlogtds.controller.dto.RoutineStatsDTO;
import io.github.matheusplaza.fitlogtds.model.LoggedExercise;
import io.github.matheusplaza.fitlogtds.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession,Long> {

    // Query 1: Busca as sessões e o PRIMEIRO nível de filhos (loggedExercises)
    @Query("SELECT DISTINCT ws FROM WorkoutSession ws LEFT JOIN FETCH ws.loggedExercises WHERE ws.id = :id")
    Optional<WorkoutSession> findByIdWithExercises(Long id);

    // Query 2: Busca os LoggedExercises (já com a sessão) e o SEGUNDO nível de filhos (sets)
    @Query("SELECT DISTINCT le FROM LoggedExercise le LEFT JOIN FETCH le.sets WHERE le.session.id = :sessionId")
    List<LoggedExercise> findLoggedExercisesWithSetsBySessionId(Long sessionId);

    @Query("SELECT DISTINCT ws FROM WorkoutSession ws " +
            "LEFT JOIN FETCH ws.loggedExercises " +
            "LEFT JOIN FETCH ws.user " + // Adicione esta linha
            "ORDER BY ws.sessionDate DESC") // Bônus: ordene o histórico
    List<WorkoutSession> findAllWithExercises();

    @Query("SELECT DISTINCT le FROM LoggedExercise le " +
            "LEFT JOIN FETCH le.sets " +
            "LEFT JOIN FETCH le.exercise " +
            "WHERE le IN :loggedExercises")
    List<LoggedExercise> findLoggedExercisesWithSets(List<LoggedExercise> loggedExercises);

    @Query("""
    SELECT new io.github.matheusplaza.fitlogtds.controller.dto.RoutineStatsDTO(
        wr.name,
        COUNT(ws.id),
        AVG(ws.durationInMinutes)
    )
    FROM WorkoutSession ws
    JOIN ws.workoutRoutine wr
    WHERE ws.user.id = :userId AND ws.sessionDate BETWEEN :start AND :end
    GROUP BY wr.name
""")
    List<RoutineStatsDTO> findRoutineStats(@Param("userId") Long userId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    Long countByUserIdAndSessionDateBetween(Long userId, LocalDate start, LocalDate end);
}
