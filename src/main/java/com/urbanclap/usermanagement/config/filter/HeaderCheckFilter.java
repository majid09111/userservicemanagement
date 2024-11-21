package com.urbanclap.usermanagement.config.filter;
import com.urbanclap.usermanagement.constants.Constants;
import com.urbanclap.usermanagement.data.exception.ConsumerException;
import com.urbanclap.usermanagement.data.exception.ServiceProviderException;
import com.urbanclap.usermanagement.data.pojos.UserDetails;
import com.urbanclap.usermanagement.service.AuthenticationService;
import com.urbanclap.usermanagement.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class HeaderCheckFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${metadata.headers.optional}")
    private boolean headerCheckOptional;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        }catch(ConsumerException | ServiceProviderException e){
            log.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }
}
