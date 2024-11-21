package com.urbanclap.usermanagement.entity;

import com.urbanclap.usermanagement.data.enums.UserType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="userType")
    @Enumerated(EnumType.STRING)
    private UserType userType;

}
