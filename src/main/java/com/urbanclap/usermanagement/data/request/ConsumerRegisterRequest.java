package com.urbanclap.usermanagement.data.request;

import lombok.Data;

@Data
public class ConsumerRegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long phoneNumber;

}
