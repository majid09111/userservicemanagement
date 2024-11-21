package com.urbanclap.usermanagement.controllers;

import com.urbanclap.usermanagement.data.request.ServiceProviderRegisterRequest;
import com.urbanclap.usermanagement.data.response.BaseResponse;
import com.urbanclap.usermanagement.data.response.ServiceProviderResponse;
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
@RequestMapping("/api/v1/serviceProvider")
@CrossOrigin
@Slf4j
public class ServiceProviderController {

    @Autowired
    private ServiceProviderService serviceProviderService;

    @PreAuthorize("hasRole(SERVICE_PROVIDER)")
    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<ServiceProviderResponse>> registerUser(@RequestBody @Valid ServiceProviderRegisterRequest request){
       ResponseUtil<ServiceProviderResponse> responseUtil = new ResponseUtil<>();
       return responseUtil.getResponse(()-> serviceProviderService.registerServiceProvider(request));

    }

}
