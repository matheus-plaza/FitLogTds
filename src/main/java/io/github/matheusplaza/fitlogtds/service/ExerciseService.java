package io.github.matheusplaza.fitlogtds.service;

import io.github.matheusplaza.fitlogtds.controller.dto.ExerciseDTO;
import io.github.matheusplaza.fitlogtds.exceptions.NotFoundException;
import io.github.matheusplaza.fitlogtds.mapper.ExerciseMapper;
import io.github.matheusplaza.fitlogtds.model.Exercise;
import io.github.matheusplaza.fitlogtds.model.User;
import io.github.matheusplaza.fitlogtds.repository.ExerciseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository repository;
    private final ExerciseMapper mapper;
    private final UserContextService userContextService;

    //metodo auxiliar
    public Exercise findById(Long exerciseId) {
        return repository.findById(exerciseId)
                        .orElseThrow(() ->  new NotFoundException("Exercicio nao encontrado"));

    }

    public List<ExerciseDTO> getListExercises() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public ExerciseDTO getExercise(Long id) {
                return mapper.toDTO(findById(id));
    }

    @Transactional
    public ExerciseDTO saveExercise(ExerciseDTO exerciseDTO) {
        User currentUser = userContextService.getCurrentUser();
        Exercise exercise = mapper.toEntity(exerciseDTO);
        exercise.setCreatedBy(currentUser);

        return mapper.toDTO(repository.save(exercise));
    }

    @Transactional
    public ExerciseDTO updateExercise(Long id, ExerciseDTO dto) {
        User currentUser = userContextService.getCurrentUser();
        Exercise exc = findById(id);

        if (exc.getCreatedBy() == null || !exc.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado: Você não pode alterar este exercício.");
        }

        exc.setName(dto.name());
        exc.setMuscleGroup(dto.muscleGroup());

        return mapper.toDTO(exc); // Transactional faz o save
    }

    @Transactional
    public void deleteExercise(Long id) {
        User currentUser = userContextService.getCurrentUser();
        Exercise exc = findById(id);

        if (exc.getCreatedBy() == null || !exc.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado: Você não pode deletar este exercício.");
        }

        repository.delete(exc);
    }
    public List<Exercise> getListExercise(Set<Long> ids) {
        return repository.findAllById(ids);
    }
}
