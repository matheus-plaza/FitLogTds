package io.github.matheusplaza.fitlogtds.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardDTO(
        Long workoutsLast30Days,
        BigDecimal totalVolumeLast30Days,
        List<RoutineStatsDTO> routineDistribution,
        List<ExerciseAnalyticsDTO> exerciseProgress

) {
}
