package com.sergs.bank.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.sergs.bank.model.dto.NewUserDto;
import com.sergs.bank.model.dto.UserDto;
import com.sergs.bank.model.entity.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toDto(UserEntity entity);
    UserEntity toEntity(NewUserDto dto);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    UserEntity updateEntityFromDto(@MappingTarget UserEntity entity, UserDto dto);
}
