package com.urbanclap.usermanagement.entity;

import com.urbanclap.usermanagement.data.enums.ServiceProviderType;
import com.urbanclap.usermanagement.data.enums.UserType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="service_provider")
public class ServiceProvider {

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

    @Column(name="serviceProviderType")
    @Enumerated(EnumType.STRING)
    private ServiceProviderType serviceProvider;

    @Column(name="isVerified")
    private Boolean isVerified;

}
