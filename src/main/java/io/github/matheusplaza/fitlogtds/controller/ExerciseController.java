package io.github.matheusplaza.fitlogtds.Controller;

import io.github.matheusplaza.fitlogtds.model.Exercise;
import io.github.matheusplaza.fitlogtds.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;

    @GetMapping
    public ResponseEntity<List<Exercise>> getExercises() {
        exerciseRepository.findAll();
        return ResponseEntity.ok(exerciseRepository.findAll());
    }
}
