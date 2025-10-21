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

    @Query("SELECT new io.github.matheusplaza.fitlogtds.controller.dto.ExerciseAnalyticsDTO(" +
            "le.exercise.id, le.exercise.name, MAX(s.weight), MAX(s.weight), MAX(le.session.sessionDate)) " +
            "FROM LoggedSet s JOIN s.loggedExercise le " +
            "JOIN le.session ws " + // Adicionei um join na sessão aqui
            "WHERE ws.user.id = :userId " + // E filtrei pelo user da sessão
            "GROUP BY le.exercise.id, le.exercise.name")
    List<ExerciseAnalyticsDTO> findExerciseAnalytics(@Param("userId") Long userId);

    @Query("SELECT SUM(ls.weight * ls.repetitions) " +
            "FROM LoggedSet ls " +
            "JOIN ls.loggedExercise le " +
            "JOIN le.session s " +
            "WHERE s.user.id = :userId AND s.sessionDate BETWEEN :start AND :end")
    BigDecimal calculateTotalVolume(@Param("userId") Long userId, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
