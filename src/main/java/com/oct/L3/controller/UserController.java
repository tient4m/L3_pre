package com.oct.L3.controller;

import com.oct.L3.dtos.LoginDTO;
import com.oct.L3.dtos.UserDTO;
import com.oct.L3.dtos.response.ResponseObject;
import com.oct.L3.dtos.response.UserLoginResponse;
import com.oct.L3.dtos.response.UserResponse;
import com.oct.L3.mapper.UserMapper;
import com.oct.L3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createUser(@RequestBody UserDTO userDTO) {
        UserResponse userResponse = userMapper.toResponse(userService.createUser(userDTO));
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("User created successfully")
                .status(HttpStatus.CREATED)
                .data(userResponse)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDTO loginDTO) throws Exception {
        UserLoginResponse userLoginResponse = userService.login(loginDTO.getUserName(), loginDTO.getPassword());
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("User logged in successfully")
                .status(HttpStatus.OK)
                .data(userLoginResponse)
                .build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        UserResponse userResponse = userMapper.toResponse(userService.updateUser(id, userDTO));
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("User updated successfully")
                .status(HttpStatus.OK)
                .data(userResponse)
                .build());
    }



}
