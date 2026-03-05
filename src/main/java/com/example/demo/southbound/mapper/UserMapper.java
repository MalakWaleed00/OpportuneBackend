package com.example.demo.southbound.mapper;

import com.example.demo.domain.dto.auth.AuthResponseDTO;
import com.example.demo.domain.dto.auth.RegisterRequestDTO;
import com.example.demo.southbound.entity.Skill;
import com.example.demo.southbound.entity.User;
import com.example.demo.southbound.entity.UserSkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "userSkills", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "workExperiences", ignore = true)
    @Mapping(target = "username", source = "username")
    User toEntity(RegisterRequestDTO dto);

    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "token", ignore = true)
    AuthResponseDTO toAuthResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "yearsOfExperience", ignore = true)
    UserSkill toUserSkill(User user, Skill skill);
}