package com.oct.L3.service.impl;

import com.oct.L3.dtos.response.UserLoginResponse;
import com.oct.L3.configurations.JWTTokenUtil;
import com.oct.L3.entity.UserEntity;
import com.oct.L3.mapper.UserMapper;
import com.oct.L3.dtos.PositionDTO;
import com.oct.L3.dtos.UserDTO;
import com.oct.L3.repository.PositionRepository;
import com.oct.L3.repository.UserRepository;
import com.oct.L3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final PositionRepository positionRepository;
    private final JWTTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    @Override
    public UserLoginResponse login(String userName, String password) throws Exception {
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("UserEntity not found"));
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        PositionDTO positionDTO = modelMapper.map(userEntity.getPositionId(), PositionDTO.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userName, password, userEntity.getAuthorities()
        );
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = jwtTokenUtil.generateToken(userEntity);
        return UserLoginResponse.builder()
                .userId(userEntity.getId())
                .userName(userEntity.getUsername())
                .role(userEntity.getRole())
                .fullName(userEntity.getFullName())
                .positionDTO(positionDTO)
                .token(token)
                .build();
    }

    @Override
    public void delete(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("UserEntity not found");
        }
        userRepository.deleteById(userId);
    }
}
