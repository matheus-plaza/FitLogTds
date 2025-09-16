package io.github.matheusplaza.fitlogtds.controller.dto;

import java.math.BigDecimal;

public record LoggedSetDTO(Integer setNumber,
                           Integer repetitions,
                           BigDecimal weight,
                           String notes) {
}