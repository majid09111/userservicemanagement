package com.urbanclap.usermanagement.controllers;

import com.urbanclap.usermanagement.data.request.ConsumerRegisterRequest;
import com.urbanclap.usermanagement.data.response.BaseResponse;
import com.urbanclap.usermanagement.data.response.ConsumerResponse;
import com.urbanclap.usermanagement.entity.Consumer;
import com.urbanclap.usermanagement.service.ConsumerService;
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
@RequestMapping("/api/v1/consumer")
@CrossOrigin
@Slf4j
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @PreAuthorize("hasRole(CONSUMER)")
    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<ConsumerResponse>> registerConsumer(@RequestBody @Valid ConsumerRegisterRequest request){
        ResponseUtil<ConsumerResponse> responseUtil = new ResponseUtil<>();
        return responseUtil.getResponse(()-> consumerService.registerConsumer(request));
    }

}
