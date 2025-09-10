package io.github.matheusplaza.fitlogtds.service;

import io.github.matheusplaza.fitlogtds.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository repository;
}
