package com.oct.L3.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userId;
    private String userName;
    private String fullName;
    private String password;
    private String role;
    private PositionDTO position;
}
