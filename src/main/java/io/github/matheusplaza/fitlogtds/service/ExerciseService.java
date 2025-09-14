package io.github.matheusplaza.fitlogtds.service;

import io.github.matheusplaza.fitlogtds.controller.dto.ExerciseDTO;
import io.github.matheusplaza.fitlogtds.exceptions.NotFoundException;
import io.github.matheusplaza.fitlogtds.mapper.ExerciseMapper;
import io.github.matheusplaza.fitlogtds.model.Exercise;
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

    public ExerciseDTO saveExercise(ExerciseDTO exerciseDTO) {
        return mapper.toDTO(repository.save(mapper.toEntity(exerciseDTO)));
    }

    @Transactional
    public ExerciseDTO updateExercise(Long id,  ExerciseDTO dto) {
        return repository.findById(id).map( exc -> {
            exc.setName(dto.name());
            exc.setCreatedBy(exc.getCreatedBy());
            exc.setMuscleGroup(dto.muscleGroup());

            return mapper.toDTO(exc);
            //nao eh necessario usar o save aqui pois a conexao com o bdd nao foi fechada
        }).orElseThrow(() -> new NotFoundException("Exercicio nao encontrado"));
    }

    public void deleteExercise(Long id) {
        repository.deleteById(id);
    }

    public List<Exercise> getListExercise(Set<Long> ids) {
        return repository.findAllById(ids);
    }
}
