package com.oct.L3.convertTo;

import com.oct.L3.dtos.UserDTO;
import com.oct.L3.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {


    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .role(user.getRole())
                .position(user.getPosition().getId())
                .build();
    }
}
