package org.example.liba.service;

import org.example.liba.entity.User;
import org.example.liba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean registerUser(String userId, String password, String firstName, String lastName){
        if(userRepository.existsByUserId(userId)){
            return false;
        }
        User newUser = new User(userId, password, firstName, lastName);
        userRepository.save(newUser);
        return true;
    }

    public String getFullName(String userId){
        User user = userRepository.findByUserId(userId);
        if(user != null){
            return user.getFirstName() + " " + user.getLastName();
        }
        return "Unknown";
    }

    public boolean verifyLogin(String userId, String password){
        User user = userRepository.findByUserId(userId);
        if(user != null){
            System.out.println("User found: " + user.getUserId());
            if(user.getPassword().equals(password)){
                return true;
            }
            else{
                System.out.println("Password does not match.");
            }
        }
        else{
            System.out.println("User not found.");
        }
        return false;
    }
}
