package com.oct.L3.service;

import com.oct.L3.dtos.response.UserLoginResponse;
import com.oct.L3.dtos.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserLoginResponse login(String userName, String password) throws Exception;

    void delete(Integer userId);

    UserDTO updateUser(Integer userId, UserDTO userDTO);
}
