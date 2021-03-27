# Authentication Microservice

In this micro service we will be simulate simple authentication mechanism and enhance security by encrypting the password before storing it into DB

# Technical Stack

The Technical Stack composed by :
- **Spring Boot**:
    - Spring Data JPA for DAO
    - Spring MVC

- **Maven Dependencies**:
    - Lombok for code Refactoring
    - Swagger & Swagger-UI for API Docs
    - SLf4J for logging
    - MYSQL JDBC Driver
    - ...

## Mandatory Features

- [ ] CRUD EndPoints for User
- [ ] Encrypt and Decrypt Password using **PBKDF2** algorithm
- [ ] Use **Github** as Code Repository
### Endpoint's
#### Post

> **/logIn**
- the upper mapping True if the credentials passed are **Login successfully** otherwise **User or Password is wrong**
    -	ex : /logIn params  => username: "admin", password:"admin"
> **/signIn**
- the upper mapping True if they **not already user in database has the same username**
    - ex: /signIn params => username:"admin", password:"admin", confirmPassword:"admin", role:admin
## Architecture
- [ ] **Config** : All the configurations beans
- [ ] **Model**: All the entities and models
- [ ] **Dao**: All the Repositories for DAO Layer
- [ ] **Service**: All the Service beans that would interact with DAO Layer and controller Layer
- [ ] **RestController**: All the endpoint's

> naming of the packages are not capitalized : **service** and not ~~Service~~

## Nice To have

Change the code the way we use Redis Database for replacing MySQL DB
> Redis is NoSQL DataBase Based on Key/Value for more infos check : [Redis Spring Boot Tutorial](https://www.baeldung.com/spring-data-redis-tutorial)

