package io.github.matheusplaza.fitlogtds.controller.dto;

import java.math.BigDecimal;

//como cada serie serie foi executada
public record LoggedSetCreateDTO(
        int setNumber,
        Integer repetitions,
        BigDecimal weight,
        String notes
) {
}