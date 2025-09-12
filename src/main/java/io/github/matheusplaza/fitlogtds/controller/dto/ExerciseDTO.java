package io.github.matheusplaza.fitlogtds.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExerciseDTO(Long id,
                          @NotBlank(message = "O nome não pode ser nulo ou vazio")
                          @Size(min = 3, message = "Nome deve ter no minimo 3 letras")
                          String name,
                          @NotBlank(message = "O grupo muscular não pode ser nulo ou vazio")
                          @Size(min = 3, message= "Grupo muscular deve ter no minimo 3 letras")
                          String muscleGroup,
                          Long createdByUserId) { }

