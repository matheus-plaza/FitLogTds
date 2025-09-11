package io.github.matheusplaza.fitlogtds.mapper;

import io.github.matheusplaza.fitlogtds.controller.dto.UserDTO;
import io.github.matheusplaza.fitlogtds.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDTO userDTO);

}