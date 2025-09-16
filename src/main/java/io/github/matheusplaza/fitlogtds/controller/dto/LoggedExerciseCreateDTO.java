package io.github.matheusplaza.fitlogtds.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

//qual exercicio responsavel pela lista de series
public record LoggedExerciseCreateDTO(
        @NotNull
        Long exerciseId, // O ID do exercício do nosso catálogo

        List<LoggedSetCreateDTO> sets
) {
}