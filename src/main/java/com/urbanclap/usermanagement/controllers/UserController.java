package com.urbanclap.usermanagement.controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.urbanclap.usermanagement.data.request.JwtRequest;
import com.urbanclap.usermanagement.data.response.BaseResponse;
import com.urbanclap.usermanagement.data.response.JwtResponse;
import com.urbanclap.usermanagement.service.AuthenticationService;
import com.urbanclap.usermanagement.service.ServiceProviderService;
import com.urbanclap.usermanagement.utils.ResponseUtil;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    @PreAuthorize("hasAnyRole(CONSUMER,SERVICE_PROVIDER)")
    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<JwtResponse>> login(@RequestBody @Valid JwtRequest request){
        ResponseUtil<JwtResponse> responseUtil = new ResponseUtil<>();
        return responseUtil.getResponse(()-> authenticationService.login(request));
    }

    @PreAuthorize("hasRole(ADMIN)")
    @RequestMapping(value = "/verify/serviceProvider", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> verifyServiceProvider(@RequestParam(name="serviceProviderId")Long serviceProviderId){
        ResponseUtil<Boolean> responseUtil = new ResponseUtil<>();
        return responseUtil.getResponse(()-> serviceProviderService.verifyServiceProvider(serviceProviderId));
    }

}
