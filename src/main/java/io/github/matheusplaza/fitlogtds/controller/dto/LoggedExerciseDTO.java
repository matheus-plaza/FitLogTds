package io.github.matheusplaza.fitlogtds.controller.dto;

import java.util.List;

public record LoggedExerciseDTO(ExerciseDTO exercise, List<LoggedSetDTO> sets) {
}
