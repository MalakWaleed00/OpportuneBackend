package com.example.demo.southbound.mapper;

import com.example.demo.domain.dto.parsing.CvDTO;
import com.example.demo.southbound.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CvMapper {

    CvDTO toCvDTO(User user);
}
