package io.github.matheusplaza.fitlogtds.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExerciseAnalyticsDTO(
        Long exerciseId,
        String exerciseName,
        BigDecimal personalRecord,
        BigDecimal lastWeight,
        LocalDate lastDate
) {
}
