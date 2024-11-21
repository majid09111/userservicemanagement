package com.urbanclap.usermanagement.service;

import com.urbanclap.usermanagement.data.enums.UserType;
import com.urbanclap.usermanagement.data.exception.ConsumerException;
import com.urbanclap.usermanagement.data.request.ConsumerRegisterRequest;
import com.urbanclap.usermanagement.data.response.ConsumerResponse;
import com.urbanclap.usermanagement.entity.Consumer;
import com.urbanclap.usermanagement.repositories.ConsumerRepository;
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
public class ConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;

    public ConsumerResponse registerConsumer(ConsumerRegisterRequest consumerRegisterRequest) throws NoSuchAlgorithmException, NoSuchProviderException {
        Optional<Consumer> optionalConsumer = consumerRepository.findByEmail(consumerRegisterRequest.getEmail());
        if(optionalConsumer.isPresent()){
            throw new ConsumerException("Email Already Exists. Try With a different Email", HttpStatus.BAD_REQUEST);
        }

        Consumer consumer = new Consumer();
        consumer.setEmail(consumerRegisterRequest.getEmail());
        consumer.setFirstName(consumerRegisterRequest.getFirstName());
        consumer.setLastName(consumer.getLastName());
        consumer.setUserType(UserType.CONSUMER);
        consumer.setPhoneNumber(consumerRegisterRequest.getPhoneNumber());

        byte[] salt = getSalt();
        String saltString = bytetoString(salt);
        String securePassword = getSecurePassword(consumerRegisterRequest.getPassword(), saltString);
        consumer.setSalt(saltString);
        consumer.setPassword(securePassword);

        Consumer entity=consumerRepository.save(consumer);
        return new ConsumerResponse(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhoneNumber(), entity.getUserType());
    }

    public Consumer authenticateUser(String email, String password){
        Optional<Consumer> optionalConsumerEntity = consumerRepository.findByEmail(email);
        if(!optionalConsumerEntity.isPresent()){
            throw new ConsumerException("User Does not Exists. Kindly Register", HttpStatus.BAD_REQUEST);
        }

        Consumer consumer = optionalConsumerEntity.get();
        String salt = consumer.getSalt();
        if(getSecurePassword(password, salt).equals(consumer.getPassword())){
            return consumer;
        }else{
            return null;
        }
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
