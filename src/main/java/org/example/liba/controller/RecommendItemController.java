package org.example.liba.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
public class RecommendItemController {
    @GetMapping("/recommendation")
    public ResponseEntity<ArrayNode> recommendItems(){
        // Create an ObjectMapper to work with Json
        //ObjectMapper 是 Jackson 库中的一个类，它用于在 Java 对象和 JSON 数据之间进行转换。
        ObjectMapper objectMapper = new ObjectMapper();

        // Create a JSON array node
        ArrayNode arrayNode = objectMapper.createArrayNode();

        //create 1st JSON object node
        ObjectNode user1 = objectMapper.createObjectNode();
        user1.put("name", "abc");
        user1.put("address", "san francisco");
        user1.put("time", "01/01/2017");

        ObjectNode user2 = objectMapper.createObjectNode();
        user2.put("name", "1234");
        user2.put("address", "san jose");
        user2.put("time", "01/02/2017");

        //add the object nodes to the array node
        arrayNode.add(user1);
        arrayNode.add(user2);

        // return the JSON array as a string in the response
        return ResponseEntity.ok(arrayNode);
    }
}
