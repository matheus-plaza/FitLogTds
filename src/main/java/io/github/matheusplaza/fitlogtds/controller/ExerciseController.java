package io.github.matheusplaza.fitlogtds.controller;

import io.github.matheusplaza.fitlogtds.controller.dto.ExerciseDTO;
import io.github.matheusplaza.fitlogtds.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController implements io.github.matheusplaza.fitlogtds.Controller.GenericController {

    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getListExercises() {
        return ResponseEntity.ok(exerciseService.getListExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getExercise(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getExercise(id));
    }

    @PostMapping
    public ResponseEntity<ExerciseDTO> saveExercise(@RequestBody @Valid ExerciseDTO exercise) {
        ExerciseDTO dto = exerciseService.saveExercise(exercise);
        URI location = gerarHeadLocation(dto.id());
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDTO> updateExercise(@RequestBody @Valid ExerciseDTO exercise, @PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.updateExercise(id,exercise));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }

}