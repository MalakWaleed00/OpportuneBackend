package com.example.demo.southbound.mapper;

import com.example.demo.domain.dto.auth.AuthResponseDTO;
import com.example.demo.domain.dto.auth.RegisterRequestDTO;
import com.example.demo.domain.dto.auth.UserProfileDTO;
import com.example.demo.southbound.entity.Skill;
import com.example.demo.southbound.entity.User;
import com.example.demo.southbound.entity.UserSkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Mapping(target = "id", source = "id")
    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "username", expression = "java(user.getProfileUsername())")
    @Mapping(target = "skills", expression = "java(toSkillNames(user))")
    AuthResponseDTO toAuthResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "yearsOfExperience", ignore = true)
    UserSkill toUserSkill(User user, Skill skill);

    default Set<String> toSkillNames(User user) {
        if (user.getUserSkills() == null) {
            return Collections.emptySet();
        }

        return user.getUserSkills()
                .stream()
                .map(UserSkill::getSkill)
                .filter(java.util.Objects::nonNull)
                .map(Skill::getName)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));
    }

}
