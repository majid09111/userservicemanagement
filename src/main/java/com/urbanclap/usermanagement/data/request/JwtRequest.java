package com.urbanclap.usermanagement.data.request;

import com.urbanclap.usermanagement.data.enums.UserType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JwtRequest{

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private UserType role;

}
