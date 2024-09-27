package com.oct.L3.service.impl;

import com.oct.L3.Response.UserLoginResponse;
import com.oct.L3.configurations.JWTTokenUtil;
import com.oct.L3.convertTo.UserMapper;
import com.oct.L3.dtos.PositionDTO;
import com.oct.L3.dtos.UserDTO;
import com.oct.L3.entity.User;
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
    public UserDTO save(UserDTO userDTO) {
        User user = User.builder()
                .userName(userDTO.getUserName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .fullName(userDTO.getFullName())
                .position(positionRepository.findById(userDTO.getPosition()).orElseThrow(() -> new RuntimeException("Position not found")))
                .role(userDTO.getRole())
                .build();
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserLoginResponse login(String userName, String password) throws Exception {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        PositionDTO positionDTO = modelMapper.map(user.getPosition(), PositionDTO.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userName, password
        );
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = jwtTokenUtil.generateToken(user);
        return UserLoginResponse.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .role(user.getRole())
                .fullName(user.getFullName())
                .positionDTO(positionDTO)
                .token(token)
                .build();
    }

    @Override
    public void delete(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }
}
