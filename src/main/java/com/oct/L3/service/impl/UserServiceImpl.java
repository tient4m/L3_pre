package com.oct.L3.service.impl;

import com.oct.L3.dtos.UserDTO;
import com.oct.L3.entity.User;
import com.oct.L3.repository.UserRepository;
import com.oct.L3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = User.builder()
                .Id(userDTO.getUserId())
                .userName(userDTO.getUserName())
                .password(userDTO.getPassword())
                .build();
        userRepository.save(user);
        return userDTO;
    }

    @Override
    public void delete(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }
}
