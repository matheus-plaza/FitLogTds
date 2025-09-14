package io.github.matheusplaza.fitlogtds.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record LoggedExerciseCreateDTO(
        @NotNull
        Long exerciseId, // O ID do exercício do nosso catálogo

        List<LoggedSetCreateDTO> sets
) {
}