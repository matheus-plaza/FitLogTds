package io.github.matheusplaza.fitlogtds.controller;

import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutRoutineCreateDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutRoutineDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutRoutineUpdate;
import io.github.matheusplaza.fitlogtds.service.WorkoutRoutineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routines")
public class WorkoutRoutineController implements GenericController {

    private final WorkoutRoutineService service;

    @GetMapping
    public ResponseEntity<List<WorkoutRoutineDTO>> getListRoutines() {
        return ResponseEntity.ok(service.getListRoutines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutRoutineDTO> getRoutine(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRoutine(id));
    }

    @PostMapping
    public ResponseEntity<WorkoutRoutineDTO> saveRoutine(@RequestBody @Valid WorkoutRoutineCreateDTO dto) {
        WorkoutRoutineDTO routine = service.saveRoutine(dto);
        URI location = gerarHeadLocation(routine.id());
        return ResponseEntity.created(location).body(routine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutRoutineDTO> updateRoutine(@PathVariable Long id, @RequestBody @Valid WorkoutRoutineUpdate dto) {
        return ResponseEntity.ok(service.updateRoutine(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable Long id) {
        service.deleteRoutine(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{routineId}/exercises/{exerciseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addExerciseRoutine(@PathVariable Long routineId, @PathVariable Long exerciseId) {
        service.addExerciseRoutine(routineId, exerciseId);
    }

    @DeleteMapping("/{routineId}/exercises/{exerciseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExerciseRoutine(@PathVariable Long routineId, @PathVariable Long exerciseId) {
        service.deleteExerciseRoutine(routineId, exerciseId);
    }


}
