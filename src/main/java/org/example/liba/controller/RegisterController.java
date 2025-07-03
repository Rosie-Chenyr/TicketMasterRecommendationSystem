package org.example.liba.controller;

import org.example.liba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String body) throws IOException{
        Map<String, Object> input = ControllerHelper.readJsonFromBody(body);
        String userId = (String) input.get("userId");
        String password = (String) input.get("password");
        String firstName = (String) input.get("firstName");
        String lastName = (String) input.get("lastName");

        boolean success = userService.registerUser(userId, password, firstName,lastName);

        if(success){
            return  ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        else{
            return ResponseEntity.badRequest().body("{\"status\": \"User Already Exists\"}");
        }
    }
}
