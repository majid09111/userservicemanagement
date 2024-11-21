package com.urbanclap.usermanagement.utils;

import com.urbanclap.usermanagement.data.enums.UserType;
import com.urbanclap.usermanagement.data.exception.AuthenticationException;
import com.urbanclap.usermanagement.data.pojos.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.urbanclap.usermanagement.constants.Constants.TOKEN_EXPIRED;

@Component
@Slf4j
public class JwtTokenUtil {

    public static final long JWT_TOKEN_VALIDITY = 4320 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private LocaleUtil localeUtil;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getEmail());
        claims.put("id",userDetails.getId());
        claims.put("role",userDetails.getRole());
        return doGenerateToke(claims, userDetails.getEmail());
    }

    public String doGenerateToke(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getUsernameFromToken(String token) throws AuthenticationException {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public UserType getRoleFromToken(String token) throws AuthenticationException {
        return UserType.valueOf(getAllClaimsFromToken(token).get("role").toString());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws AuthenticationException{
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) throws AuthenticationException {

        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e){
            log.error("JWT Token has expired");
            throw new AuthenticationException(localeUtil.getLocalizedString(TOKEN_EXPIRED), HttpStatus.UNAUTHORIZED);
        }
    }


}
