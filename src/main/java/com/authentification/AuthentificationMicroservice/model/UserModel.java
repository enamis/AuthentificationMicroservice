package com.authentification.AuthentificationMicroservice.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserModel {

    @Id
    private String userName;

    @Column
    private String userPassword;

}
