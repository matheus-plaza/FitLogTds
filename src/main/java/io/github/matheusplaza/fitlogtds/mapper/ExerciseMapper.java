package io.github.matheusplaza.fitlogtds.mapper;

import io.github.matheusplaza.fitlogtds.controller.dto.ExerciseDTO;
import io.github.matheusplaza.fitlogtds.model.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    @Mapping(source = "createdBy.id", target = "createdByUserId")
    ExerciseDTO toDTO(Exercise exercise);

    Exercise toEntity(ExerciseDTO exerciseDTO);

    List<ExerciseDTO> toDTO(List<Exercise> exercises);

    Set<ExerciseDTO> toDTO(Set<Exercise> exercises);

}