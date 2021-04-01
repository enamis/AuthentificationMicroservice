package com.authentification.AuthentificationMicroservice.dao;


import com.authentification.AuthentificationMicroservice.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<UserModel,String> {

     UserModel findByUserName(String username);
}
