package com.urbanclap.usermanagement.data.response;

import com.urbanclap.usermanagement.data.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private UserType userType;
}
