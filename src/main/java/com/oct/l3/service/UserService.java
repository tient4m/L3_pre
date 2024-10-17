package com.oct.l3.service;

import com.oct.l3.dtos.response.UserLoginResponse;
import com.oct.l3.dtos.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserLoginResponse login(String userName, String password) throws Exception;

    void delete(Integer userId);

    UserDTO updateUser(Integer userId, UserDTO userDTO);
}
