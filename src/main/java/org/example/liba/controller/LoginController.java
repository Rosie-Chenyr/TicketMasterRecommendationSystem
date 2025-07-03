package org.example.liba.controller;

import org.example.liba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody String body, HttpSession session){
        try {
            Map<String, Object> input = ControllerHelper.readJsonFromBody(body);
            String userId = (String) input.get("userId");
            String password = (String) input.get("password");

            if(userService.verifyLogin(userId, password)){
                session.setAttribute("user_id", userId);
                System.out.println("Session after login: " + session.getId());
                String fullName = userService.getFullName(userId);
                return ResponseEntity.ok("Login Successful. Welcome, " + fullName);
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed. Invalid credentials.");
            }
        } catch (IOException e){
            return ResponseEntity.badRequest().body("Invalid request body");
        }
    }

    @GetMapping
    public ResponseEntity<String> checkSession(HttpSession session){
        if(session.getAttribute("user_id") != null){
            String userId = session.getAttribute("user_id").toString();
            return ResponseEntity.ok("Session is active for user: " + userService.getFullName(userId));
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired. Please log in again.");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("You have been logged out.");
    }
}
