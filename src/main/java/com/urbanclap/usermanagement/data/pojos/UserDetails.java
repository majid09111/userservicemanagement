package com.urbanclap.usermanagement.data.pojos;

import com.urbanclap.usermanagement.data.enums.UserType;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class UserDetails extends User {

    private String email;
    private Long id;
    private UserType role;
    private String password;

    public UserDetails(String email, String password, Long id, UserType role, Collection<? extends GrantedAuthority> authorities){
        super(email,password, authorities);
        this.email=email;
        this.password=password;
        this.id=id;
        this.role = role;
    }
}
