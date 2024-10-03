package com.oct.L3.mapper;

import com.oct.L3.dtos.UserDTO;
import com.oct.L3.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserEntity toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public UserDTO toDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }
}
