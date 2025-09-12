package io.github.matheusplaza.fitlogtds.exceptions;

import java.time.Instant;

public record StandardError(
        Instant timestamp,
        Integer status,
        String message,
        String path
){
}
