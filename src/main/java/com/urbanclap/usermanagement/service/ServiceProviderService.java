package com.urbanclap.usermanagement.service;

import com.urbanclap.usermanagement.data.enums.UserType;
import com.urbanclap.usermanagement.data.exception.ServiceProviderException;
import com.urbanclap.usermanagement.data.request.ServiceProviderRegisterRequest;
import com.urbanclap.usermanagement.data.response.ServiceProviderResponse;
import com.urbanclap.usermanagement.entity.ServiceProvider;
import com.urbanclap.usermanagement.repositories.ServiceProviderRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Optional;

@Service
public class ServiceProviderService {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    public ServiceProviderResponse registerServiceProvider(ServiceProviderRegisterRequest serviceProviderRegisterRequest) throws NoSuchAlgorithmException, NoSuchProviderException {
        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository.findByEmail(serviceProviderRegisterRequest.getEmail());
        if(optionalServiceProvider.isPresent()){
            throw new ServiceProviderException("Email Already Exists. Try With a different Email", HttpStatus.BAD_REQUEST);
        }

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setFirstName(serviceProviderRegisterRequest.getFirstName());
        serviceProvider.setLastName(serviceProviderRegisterRequest.getLastName());
        serviceProvider.setEmail(serviceProvider.getEmail());
        serviceProvider.setPhoneNumber(serviceProvider.getPhoneNumber());
        serviceProvider.setUserType(UserType.SERVICE_PROVIDER);
        serviceProvider.setServiceProvider(serviceProviderRegisterRequest.getServiceProviderType());

        byte[] salt = getSalt();
        String saltString = bytetoString(salt);
        String securePassword = getSecurePassword(serviceProviderRegisterRequest.getPassword(), saltString);
        serviceProvider.setSalt(saltString);
        serviceProvider.setPassword(securePassword);

        ServiceProvider entity = serviceProviderRepository.save(serviceProvider);
        return new ServiceProviderResponse(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhoneNumber(),entity.getUserType(),entity.getServiceProvider());
    }

    public ServiceProvider authenticateUser(String email, String password){
        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository.findByEmail(email);
        if(!optionalServiceProvider.isPresent()){
            throw new ServiceProviderException("UserDoes Not Exist. Kindly Register", HttpStatus.BAD_REQUEST);
        }
        ServiceProvider serviceProvider = optionalServiceProvider.get();
        String salt = serviceProvider.getSalt();
        if(getSecurePassword(password,salt).equals(serviceProvider.getPassword())){
            return  serviceProvider;
        }else{
            return null;
        }
    }

    public Boolean verifyServiceProvider(Long id){
        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository.findById(id);
        if(!optionalServiceProvider.isPresent()){
            throw new ServiceProviderException("Invalid Service Provider Id", HttpStatus.BAD_REQUEST);
        }

        ServiceProvider serviceProvider = optionalServiceProvider.get();
        serviceProvider.setIsVerified(true);
        serviceProviderRepository.save(serviceProvider);
        return true;
    }

    public static  byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt
        byte[] salt = new byte[20];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt;
    }

    public static  String bytetoString(byte[] input) {
        return org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(input);
    }

    public static  String getSecurePassword(String passwordToHash, String salt)
    {
        String generatedPassword = null;
        try {

            byte[] saltBytes = stringToByte(salt);
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(saltBytes);
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());

            generatedPassword = bytetoString(bytes);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    public static  byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);

        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }
}
