package com.urbanclap.usermanagement.utils;

import com.urbanclap.usermanagement.data.exception.AuthenticationException;
import com.urbanclap.usermanagement.data.exception.ConsumerException;
import com.urbanclap.usermanagement.data.exception.ServiceProviderException;
import com.urbanclap.usermanagement.data.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

import static com.urbanclap.usermanagement.constants.Constants.*;

@Component
@Slf4j
public class ResponseUtil<T> {

    public ResponseEntity<BaseResponse<T>> getResponse(IResponse<T> r){
        try{
            return new ResponseEntity<>(new BaseResponse<>(SUCCESS_STATUS_CODE, SUCCESS_MESSAGE, r.exec()), HttpStatus.OK);
        }
        catch (ConsumerException ex){
            log.error("API Exception thrown ",ex);
            if(ex.getHttpStatusCode() != null)
            {
                return new ResponseEntity<>(new BaseResponse<>(ex.getHttpStatusCode().value(), ex.getMessage()),ex.getHttpStatusCode());
            }
            return new ResponseEntity<>(new BaseResponse<>(NOT_FOUND_CODE, ex.getMessage()),HttpStatus.NOT_FOUND);
        }
        catch (ServiceProviderException ex){
            log.error("API Exception thrown ",ex);
            if(ex.getHttpStatusCode() != null)
            {
                return new ResponseEntity<>(new BaseResponse<>(ex.getHttpStatusCode().value(), ex.getMessage()),ex.getHttpStatusCode());
            }
            return new ResponseEntity<>(new BaseResponse<>(NOT_FOUND_CODE, ex.getMessage()),HttpStatus.NOT_FOUND);
        }
        catch (AuthenticationException ex){
            log.error("API Exception thrown ",ex);
            if(ex.getHttpStatusCode() != null)
            {
                return new ResponseEntity<>(new BaseResponse<>(ex.getHttpStatusCode().value(), ex.getMessage()),ex.getHttpStatusCode());
            }
            return new ResponseEntity<>(new BaseResponse<>(NOT_FOUND_CODE, ex.getMessage()),HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Generic Exception thrown ",ex);
            return new ResponseEntity<>(new BaseResponse<>(INTERNAL_SERVER_ERROR_CODE, ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
