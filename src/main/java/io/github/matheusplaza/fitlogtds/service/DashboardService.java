package io.github.matheusplaza.fitlogtds.service;

import io.github.matheusplaza.fitlogtds.controller.dto.DashboardDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.ExerciseAnalyticsDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.RoutineStatsDTO;
import io.github.matheusplaza.fitlogtds.repository.LoggedSetRepository;
import io.github.matheusplaza.fitlogtds.repository.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final LoggedSetRepository loggedSetRepository;

    @Transactional(readOnly = true)
    public DashboardDTO getDashboardData(Long userId) {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);

        Long workoutsLast30Days = workoutSessionRepository.countByUserIdAndSessionDateBetween(userId, startDate, endDate);

        BigDecimal totalVolumeLast30Days = Optional.ofNullable(
                loggedSetRepository.calculateTotalVolume(userId, startDate, endDate)
        ).orElse(BigDecimal.ZERO); // Garante que não será nulo

        List<RoutineStatsDTO> routineDistribution = workoutSessionRepository.findRoutineStats(userId, startDate, endDate);

        List<ExerciseAnalyticsDTO> exerciseProgress = loggedSetRepository.findExerciseAnalytics(userId);

        return new DashboardDTO(
                workoutsLast30Days,
                totalVolumeLast30Days,
                routineDistribution,
                exerciseProgress
        );
    }
}
