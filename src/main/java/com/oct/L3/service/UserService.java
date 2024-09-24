package com.oct.L3.service;

import com.oct.L3.dtos.UserDTO;

public interface UserService {
    UserDTO save(UserDTO userDTO);

    void delete(Integer userId);
}
