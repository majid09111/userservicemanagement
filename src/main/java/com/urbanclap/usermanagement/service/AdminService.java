package com.urbanclap.usermanagement.service;

import com.urbanclap.usermanagement.data.exception.ConsumerException;
import com.urbanclap.usermanagement.entity.Admin;
import com.urbanclap.usermanagement.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin authenticateUser(String email, String password){
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if(!optionalAdmin.isPresent()){
            throw new ConsumerException("No admin found", HttpStatus.BAD_REQUEST);
        }

        Admin admin = optionalAdmin.get();
        if(admin.getPassword().equals(password)){
            return admin;
        }else{
            return null;
        }
    }

}
