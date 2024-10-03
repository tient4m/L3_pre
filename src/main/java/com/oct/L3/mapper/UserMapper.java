package com.oct.L3.mapper;

import com.oct.L3.dtos.UserDTO;
import com.oct.L3.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {


    public UserEntity toEntity(UserDTO userDTO) {

        ;return UserEntity.builder()
                .id(userDTO.getId())
                .userName(userDTO.getUserName())
                .password(userDTO.getPassword())
                .fullName(userDTO.getFullName())
                .role(userDTO.getRole())
                .positionId(userDTO.getPositionId())
                .build();
    }

    public UserDTO toDTO(UserEntity userEntity) {

        return UserDTO.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUsername())
                .password(userEntity.getPassword())
                .fullName(userEntity.getFullName())
                .role(userEntity.getRole())
                .positionId(userEntity.getPositionId())
                .build();
    }
}
