package com.oct.L3.mapper;

import com.oct.L3.dtos.PositionDTO;
import com.oct.L3.dtos.UserDTO;
import com.oct.L3.dtos.response.UserResponse;
import com.oct.L3.entity.UserEntity;
import com.oct.L3.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    private final PositionRepository positionRepository;


    public UserEntity toEntity(UserDTO userDTO) {

        return UserEntity.builder()
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

    public UserResponse toResponse(UserDTO userDTO) {
        PositionDTO positionDTO = modelMapper.map(positionRepository.findById(userDTO.getPositionId()), PositionDTO.class);

        return UserResponse.builder()
                .userId(userDTO.getId())
                .userName(userDTO.getUserName())
                .role(userDTO.getRole())
                .fullName(userDTO.getFullName())
                .positionDTO(positionDTO)
                .build();
    }
}
