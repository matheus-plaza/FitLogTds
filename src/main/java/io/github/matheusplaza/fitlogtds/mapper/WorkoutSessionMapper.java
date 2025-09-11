package io.github.matheusplaza.fitlogtds.mapper;

import io.github.matheusplaza.fitlogtds.controller.dto.LoggedExerciseDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.LoggedSetDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutSessionDTO;
import io.github.matheusplaza.fitlogtds.model.LoggedExercise;
import io.github.matheusplaza.fitlogtds.model.LoggedSet;
import io.github.matheusplaza.fitlogtds.model.WorkoutSession;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ExerciseMapper.class})
public interface WorkoutSessionMapper {

    // --- Mapeamentos Principais ---

    WorkoutSessionDTO toDTO(WorkoutSession workoutSession);

    List<WorkoutSessionDTO> toDTO(List<WorkoutSession> workoutSessions);

    LoggedExerciseDTO toDTO(LoggedExercise loggedExercise);

    LoggedSetDTO toDTO(LoggedSet loggedSet);
}