package io.github.matheusplaza.fitlogtds.controller;

import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutSessionCreateDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutSessionDTO;
import io.github.matheusplaza.fitlogtds.service.WorkoutSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class WorkoutSessionController implements GenericController {
    private final WorkoutSessionService service;

    @PostMapping
    public ResponseEntity<WorkoutSessionDTO> saveSession(@RequestBody @Valid WorkoutSessionCreateDTO dto) {
        WorkoutSessionDTO savedSession = service.saveSession(dto);
        URI location = gerarHeadLocation(savedSession.id());
        return ResponseEntity.created(location).body(savedSession);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutSessionDTO>> getListSessions() {
        return ResponseEntity.ok(service.getListSessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutSessionDTO> getSession(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSession(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutSessionDTO> updateSession(@PathVariable Long id, @RequestBody @Valid  WorkoutSessionCreateDTO dto) {
        return ResponseEntity.ok(service.updateSession(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        service.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
