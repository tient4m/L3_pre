package com.oct.L3.mapper;

import com.oct.L3.dtos.UserDTO;
import com.oct.L3.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {


    public UserDTO toDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .userId(userEntity.getId())
                .userName(userEntity.getUsername())
                .fullName(userEntity.getFullName())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .position(userEntity.getPositionId().getId())
                .build();
    }
}
