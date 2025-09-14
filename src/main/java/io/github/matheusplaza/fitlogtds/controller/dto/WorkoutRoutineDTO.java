package io.github.matheusplaza.fitlogtds.controller.dto;

import java.util.Set;

public record WorkoutRoutineDTO(Long id, String name, String description, Long userId, Set<ExerciseDTO> exercises) {
}
