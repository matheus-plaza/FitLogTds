package io.github.matheusplaza.fitlogtds.mapper;

import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutRoutineDTO;
import io.github.matheusplaza.fitlogtds.model.WorkoutRoutine;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ExerciseMapper.class})
public interface WorkoutRoutineMapper {

    WorkoutRoutineDTO toDTO(WorkoutRoutine workoutRoutine);

    List<WorkoutRoutineDTO> toDTO(List<WorkoutRoutine> workoutRoutines);
}