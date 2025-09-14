package io.github.matheusplaza.fitlogtds.service;

import io.github.matheusplaza.fitlogtds.controller.dto.ExerciseDTO;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutRoutineService {

    private final WorkoutRoutineRepository routineRepository;
    private final WorkoutRoutineMapper mapper;
    private final UserService userService;
    private final ExerciseService exerciseService;


    //metodo auxiliar
    public WorkoutRoutine findById(Long id) {
        return routineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rotina de id: " + id + " nao encontrada"));
    }

    //TODO: PROTEGER (DATA TENANCY). Este método deve ser filtrado para retornar dados apenas para o usuário que esta autenticado. Será implementado no checkpoint de segurança
    @Transactional
    public WorkoutRoutineDTO getRoutine(Long id) {
        return mapper.toDTO(findById(id));
    }

    //TODO: PROTEGER (DATA TENANCY). Este método deve ser filtrado para retornar dados apenas para o usuário que esta autenticado. Será implementado no checkpoint de segurança
    public List<WorkoutRoutineDTO> getListRoutines(){
        return mapper.toDTO(routineRepository.findAllWithExercises());
    }

    @Transactional
    public WorkoutRoutineDTO saveRoutine(WorkoutRoutineCreateDTO dto) {
        User user = userService.findById(dto.userId());

        WorkoutRoutine routineEntity = new WorkoutRoutine();
        routineEntity.setName(dto.name());
        routineEntity.setDescription(dto.description());
        routineEntity.setUser(user);

        //por hora, o usuario devera adicionar apenas exercicios que ja estao criados, estou pensando em uma lista de exercicios e um botao de "+" para adicionar, inspirado em adicionar dependencia no spring initializr
        if (dto.exerciseIds() != null && !dto.exerciseIds().isEmpty()) {
            List<Exercise> exercises = exerciseService.getListExercise(dto.exerciseIds());
            routineEntity.setExercises(new HashSet<>(exercises));
        }

        WorkoutRoutine savedRoutine = routineRepository.save(routineEntity);

        return mapper.toDTO(savedRoutine);
    }

    @Transactional
    public WorkoutRoutineDTO updateRoutine(Long id, WorkoutRoutineUpdate routine){
        WorkoutRoutine wr = findById(id);
        if (routine.name() != null) {
            wr.setName(routine.name());
        }
        if (routine.description() != null) {
            wr.setDescription(routine.description());
        }
        return mapper.toDTO(wr);
    }

    public void deleteRoutine(Long id){
        routineRepository.deleteById(id);
    }

    @Transactional
    public void addExerciseRoutine(Long routineId, Long exerciseId) {
        WorkoutRoutine wr = findById(routineId);
        wr.getExercises().add(exerciseService.findById(exerciseId));
    }

    @Transactional
    public void deleteExerciseRoutine(Long routineId, Long exerciseId) {
        WorkoutRoutine wr = findById(routineId);
        wr.getExercises().remove(exerciseService.findById(exerciseId));
    }
}
