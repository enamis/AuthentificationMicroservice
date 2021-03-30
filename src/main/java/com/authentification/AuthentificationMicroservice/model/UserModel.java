package com.authentification.AuthentificationMicroservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserModel {

    @Id
    private String username;

    @Column
    private String password;

}
