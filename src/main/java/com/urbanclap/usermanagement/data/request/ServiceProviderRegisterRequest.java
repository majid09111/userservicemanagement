package com.urbanclap.usermanagement.data.request;

import com.urbanclap.usermanagement.data.enums.ServiceProviderType;
import lombok.Data;

@Data
public class ServiceProviderRegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private ServiceProviderType serviceProviderType;
    private Long phoneNumber;
}
