package org.example.liba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@RestController
public class SearchItemController {
 //   @GetMapping("/search")
//    public String searchItems(@RequestParam(value = "name", defaultValue = "World") String name){
//        return String.format("hello %s!", name);
//    }


//    public ResponseEntity<String> searchItems(@RequestParam(value = "name") String name){
//        try{
//            String message = String.format("Hello %s!", name);
//            return ResponseEntity.ok(message); //Return 200 OK with the message
//        } catch(Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error");
//        }
//    }

    public ResponseEntity<List<String>> searchItems(@RequestParam(value = "name") List<String> names){
        try{
            //List<String> names = names;
            //List<String> names = Arrays.asList("Amy", "Tom", "John");
            return ResponseEntity.ok(names); //Automatically serialized to JSON
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());// Empty list with 500 status
        }
    }

}
