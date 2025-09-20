package io.github.matheusplaza.fitlogtds.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record WorkoutSessionCreateDTO(
        @NotNull
        LocalDate sessionDate,

        Integer durationInMinutes,
        String notes,

        @NotNull
        Long userId,

        Long workoutRoutineId,

        List<LoggedExerciseCreateDTO> loggedExercises
) {
}