package com.urbanclap.usermanagement.data.response;

import com.urbanclap.usermanagement.data.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsumerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long mobileNumber;
    private UserType userType;

}
