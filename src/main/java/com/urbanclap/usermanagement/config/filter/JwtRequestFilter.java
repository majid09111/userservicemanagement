package com.urbanclap.usermanagement.config.filter;

import com.urbanclap.usermanagement.data.enums.UserType;
import com.urbanclap.usermanagement.service.AuthenticationService;
import com.urbanclap.usermanagement.utils.JwtTokenUtil;
import com.urbanclap.usermanagement.utils.LocaleUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LocaleUtil localeUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        UserType role = null;

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                role=jwtTokenUtil.getRoleFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.error("Unable to get JWT Token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            } catch (ExpiredJwtException e)
            {
                log.error("JWT Token has expired");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
            catch (Exception exception)
            {
                log.error(exception.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
                return;
            }
        } else {
            log.warn("JWT Token does not begin with Bearer String");
        }
        chain.doFilter(request, response);
    }
}
