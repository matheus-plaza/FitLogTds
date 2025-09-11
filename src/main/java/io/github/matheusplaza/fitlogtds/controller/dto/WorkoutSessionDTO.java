package io.github.matheusplaza.fitlogtds.controller.dto;

import java.time.LocalDate;
import java.util.List;

public record WorkoutSessionDTO(Long id, LocalDate sessionDate, Integer durationInMinutes, String notes, List<LoggedExerciseDTO> loggedExercises) {
}
