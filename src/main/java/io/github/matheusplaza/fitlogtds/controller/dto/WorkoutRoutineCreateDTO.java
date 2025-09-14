package io.github.matheusplaza.fitlogtds.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record WorkoutRoutineCreateDTO(
        @NotBlank @Size(min = 1)
        String name,

        String description,

        @NotNull
        Long userId,

        Set<Long> exerciseIds // Apenas os IDs dos exercícios
) {}