package io.github.matheusplaza.fitlogtds.controller.dto;

import java.math.BigDecimal;

public record LoggedSetCreateDTO(
        int setNumber,
        Integer repetitions,
        BigDecimal weight,
        String notes
) {
}