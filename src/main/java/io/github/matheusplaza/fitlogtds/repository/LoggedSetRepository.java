package io.github.matheusplaza.fitlogtds.repository;

import io.github.matheusplaza.fitlogtds.controller.dto.ExerciseAnalyticsDTO;
import io.github.matheusplaza.fitlogtds.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface LoggedSetRepository extends JpaRepository<WorkoutSession, Long> {

    // No LoggedSetRepository.java
    @Query("SELECT SUM(s.weight * s.repetitions) FROM LoggedSet s " +
            "WHERE s.loggedExercise.session.user.id = :userId " +
            "AND s.loggedExercise.session.sessionDate BETWEEN :start AND :end")
    BigDecimal calculateTotalVolume(@Param("userId") Long userId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT new io.github.matheusplaza.fitlogtds.controller.dto.ExerciseAnalyticsDTO(" +
            "le.exercise.id, le.exercise.name, MAX(s.weight), :defaultWeight, MAX(le.session.sessionDate)) " +
            "FROM LoggedSet s JOIN s.loggedExercise le " +
            "WHERE le.session.user.id = :userId " +
            "GROUP BY le.exercise.id, le.exercise.name")
    List<ExerciseAnalyticsDTO> findExerciseAnalyticsWithDefaultWeight(@Param("userId") Long userId, @Param("defaultWeight") BigDecimal defaultWeight);

    default List<ExerciseAnalyticsDTO> findExerciseAnalytics(Long userId) {
        // Ele simplesmente chama o metodo da query passando o valor zero fixo.
        return findExerciseAnalyticsWithDefaultWeight(userId, BigDecimal.ZERO);
    }
}
