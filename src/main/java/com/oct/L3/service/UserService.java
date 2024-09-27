package com.oct.L3.service;

import com.oct.L3.Response.UserLoginResponse;
import com.oct.L3.dtos.UserDTO;

public interface UserService {
    UserDTO save(UserDTO userDTO);

    UserLoginResponse login(String userName, String password) throws Exception;

    void delete(Integer userId);
}
