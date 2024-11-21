package com.urbanclap.usermanagement.service;

import com.urbanclap.usermanagement.data.response.JwtResponse;
import com.urbanclap.usermanagement.data.enums.UserType;
import com.urbanclap.usermanagement.data.exception.ConsumerException;
import com.urbanclap.usermanagement.data.exception.ServiceProviderException;
import com.urbanclap.usermanagement.data.pojos.UserDetails;
import com.urbanclap.usermanagement.data.request.JwtRequest;
import com.urbanclap.usermanagement.entity.Admin;
import com.urbanclap.usermanagement.entity.Consumer;
import com.urbanclap.usermanagement.entity.ServiceProvider;
import com.urbanclap.usermanagement.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public UserDetails loadUser(String email, String password, UserType role){
        Long id=0l;
        switch(role){
            case CONSUMER:
                Consumer consumer = consumerService.authenticateUser(email, password);
                if(Objects.isNull(consumer)){
                    throw new ConsumerException("Incorrect Username/Password", HttpStatus.BAD_REQUEST);
                }
                id=consumer.getId();
            break;
            case SERVICE_PROVIDER:
                ServiceProvider serviceProvider = serviceProviderService.authenticateUser(email,password);
                if(Objects.isNull(serviceProvider)){
                    throw new ServiceProviderException("Incorrect Username/Password", HttpStatus.BAD_REQUEST);
                }
                id=serviceProvider.getId();
            break;
            case ADMIN:
                Admin admin = adminService.authenticateUser(email,password);
                if(Objects.isNull(admin)){
                    throw new ConsumerException("Incorrect Username/Password", HttpStatus.BAD_REQUEST);
                }
                id=admin.getId();
            break;
        }
        return new UserDetails(email,password,id,role,new ArrayList<>());

    }

    public JwtResponse login(JwtRequest request){
        UserDetails userDetails = loadUser(request.getUsername(), request.getPassword(),request.getRole());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponse(token, request.getRole());
    }

}
