package com.authentification.AuthentificationMicroservice.service;

import com.authentification.AuthentificationMicroservice.controler.UserController;
import com.authentification.AuthentificationMicroservice.dao.UserDao;
import com.authentification.AuthentificationMicroservice.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserDao userDao;


    public String checkCredentials(String userName, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if(userDao.existsById(userName) && validatePassword(password,userDao.findByUserName(userName).getUserPassword())){
           log.info("stored pass "+userDao.findByUserName(userName).getUserPassword() );
            return "Successfully loggedIn";
        }
        return "username or password incorrect";
    }

    public String signIn(UserModel userModel, String passwordconfirmation) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String message;
        if (userDao.existsById(userModel.getUserName())) {
            message="username already exist, please try another one";
        } else if (userModel.getUserPassword().equals(passwordconfirmation)) {
            userModel.setUserPassword(generateStrongPasswordHash(userModel.getUserPassword()));
            userDao.save(userModel);
            message="SignIn was successfully done";
        } else {
             message="passwords non-identical";
        }
        return message;
    }


    public boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }


    public String passwordEncry(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String generatedSecuredPasswordHash = generateStrongPasswordHash(password);
        //return generatedSecuredPasswordHash;
        return generatedSecuredPasswordHash;
    }




    public static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

}
