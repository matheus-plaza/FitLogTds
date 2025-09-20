package io.github.matheusplaza.fitlogtds.controller.dto;

public record RoutineStatsDTO(
        String routineName,
        Long workoutCount,
        Double averageDurationInMinutes
) {
}
