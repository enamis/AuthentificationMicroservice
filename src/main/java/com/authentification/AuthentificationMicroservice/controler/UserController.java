package com.authentification.AuthentificationMicroservice.controler;

import com.authentification.AuthentificationMicroservice.model.UserModel;
import com.authentification.AuthentificationMicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping("/logIn")
    private String  logIn(UserModel userModel) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return userService.checkCredentials(userModel.getUserName(),userModel.getUserPassword());
    }

    @PostMapping("/signIn")
    private String signIn(UserModel userModel, String passwordconfirmation) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return userService.signIn(userModel,passwordconfirmation);
    }


}
