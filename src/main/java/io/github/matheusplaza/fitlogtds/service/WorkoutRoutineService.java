package io.github.matheusplaza.fitlogtds.service;

import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutRoutineCreateDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutRoutineDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutRoutineUpdate;
import io.github.matheusplaza.fitlogtds.exceptions.NotFoundException;
import io.github.matheusplaza.fitlogtds.mapper.WorkoutRoutineMapper;
import io.github.matheusplaza.fitlogtds.model.Exercise;
import io.github.matheusplaza.fitlogtds.model.User;
import io.github.matheusplaza.fitlogtds.model.WorkoutRoutine;
import io.github.matheusplaza.fitlogtds.repository.WorkoutRoutineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutRoutineService {

    private final WorkoutRoutineRepository routineRepository;
    private final WorkoutRoutineMapper mapper;
    private final ExerciseService exerciseService;
    private final UserContextService userContextService;

    //metodo auxiliar
    public WorkoutRoutine findById(Long id) {
        return routineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rotina de id: " + id + " nao encontrada"));
    }

    @Transactional
    public WorkoutRoutineDTO getRoutine(Long id) {
        User currentUser = userContextService.getCurrentUser();

        WorkoutRoutine wr = findById(id);

        if (!wr.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado: Esta rotina não pertence a você.");
        }

        return mapper.toDTO(wr);
    }
    @Transactional
    public List<WorkoutRoutineDTO> getListRoutines(){
        User currentUser = userContextService.getCurrentUser();
        return mapper.toDTO(routineRepository.findAllWithExercises(currentUser.getId()));
    }

    @Transactional
    public WorkoutRoutineDTO saveRoutine(WorkoutRoutineCreateDTO dto) {
        User currentUser = userContextService.getCurrentUser();

        WorkoutRoutine routineEntity = new WorkoutRoutine();
        routineEntity.setName(dto.name());
        routineEntity.setDescription(dto.description());
        routineEntity.setUser(currentUser);

        if (dto.exerciseIds() != null && !dto.exerciseIds().isEmpty()) {
            List<Exercise> exercises = exerciseService.getListExercise(dto.exerciseIds());
            routineEntity.setExercises(new HashSet<>(exercises));
        }

        WorkoutRoutine savedRoutine = routineRepository.save(routineEntity);
        return mapper.toDTO(savedRoutine);

        //por hora, o usuario devera adicionar apenas exercicios que ja estao criados, estou pensando em uma lista de exercicios e um botao de "+" para adicionar, inspirado em adicionar dependencia no spring initializr
    }
    public WorkoutRoutineDTO updateRoutine(Long id, WorkoutRoutineUpdate routine) {
        User currentUser = userContextService.getCurrentUser();
        WorkoutRoutine wr = findById(id);

        if (!wr.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado: Você não pode alterar a rotina de outro usuário.");
        }

        if (routine.name() != null) {
            wr.setName(routine.name());
        }
        if (routine.description() != null) {
            wr.setDescription(routine.description());
        }
        return mapper.toDTO(wr);
    }

    @Transactional
    public void deleteRoutine(Long id) {
        User currentUser = userContextService.getCurrentUser();
        WorkoutRoutine wr = findById(id);

        if (!wr.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado: Você não pode deletar a rotina de outro usuário.");
        }

        routineRepository.delete(wr);
    }

    @Transactional
    public void addExerciseRoutine(Long routineId, Long exerciseId) {
        User currentUser = userContextService.getCurrentUser();
        WorkoutRoutine wr = findById(routineId);

        if (!wr.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado: Você não pode alterar a rotina de outro usuário.");
        }

        wr.getExercises().add(exerciseService.findById(exerciseId));
    }

    @Transactional
    public void deleteExerciseRoutine(Long routineId, Long exerciseId) {
        User currentUser = userContextService.getCurrentUser();
        WorkoutRoutine wr = findById(routineId);

        if (!wr.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado: Você não pode alterar a rotina de outro usuário.");
        }

        wr.getExercises().remove(exerciseService.findById(exerciseId));
    }
}
