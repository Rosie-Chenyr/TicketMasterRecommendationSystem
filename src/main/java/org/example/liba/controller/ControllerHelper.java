package org.example.liba.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public class ControllerHelper {
    // Convert an object to JSON String
    public static String convertToJson(Object obj) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    //Convert JSON String to a map
    public static Map<String, Object> convertFromJson(String jsonString) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Map.class);
    }

    //Parse JSON object from request body
    public static Map<String, Object> readJsonFromBody(String body) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, Map.class);
    }

    public static ResponseEntity<String> createSuccessResponse(){
        try{
            return ResponseEntity.ok(convertToJson(Map.of("result", "SUCCESS")));
        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating response");
        }
    }
}
