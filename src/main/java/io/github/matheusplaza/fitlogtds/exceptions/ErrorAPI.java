package io.github.matheusplaza.fitlogtds.exceptions;

import java.util.List;

public record ErrorAPI(int status, String message, List<FieldMessage> fieldErrors) {
    public ErrorAPI(int status, String message) {
        this(status, message, List.of());
    }
}
