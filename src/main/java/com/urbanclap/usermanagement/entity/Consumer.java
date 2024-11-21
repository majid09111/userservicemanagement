package com.urbanclap.usermanagement.entity;

import com.urbanclap.usermanagement.data.enums.UserType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="consumer")
public class Consumer {

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

    @Column(name="phoneNumber")
    private Long phoneNumber;

    @Column(name="password")
    private String password;

    @Column(name="salt")
    private String salt;

    @Column(name="userType")
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
