package com.oct.L3.dtos.response;

import com.oct.L3.dtos.PositionDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private Integer userId;
    private String userName;
    private String role;
    private String fullName;
    private PositionDTO positionDTO;
    private String token;

}
