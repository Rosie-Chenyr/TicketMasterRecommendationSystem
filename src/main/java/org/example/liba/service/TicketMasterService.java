package org.example.liba.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.liba.entity.Category;
import org.example.liba.entity.Item;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@PropertySource("classpath:application.properties")
@Service
public class TicketMasterService {
    private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
    private static final String DEFAULT_KEYWORD = "";
    //private static final String API_KEY = "nYCoUhFQ3TtynWG12xOsVXqeOBPzQsHo";

    private final RestTemplate restTemplate;

    @Value("${ticketmaster.api.key}")
    private String apiKey;

    @Value("${ticketmaster.url}")
    private String ticketMasterURL;
    private final ObjectMapper objectMapper;


    public TicketMasterService(RestTemplate restTemplate, ObjectMapper objectMapper){
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    private List<Item> getItemList(JsonNode events){
        List<Item> itemList = new ArrayList<>();
        for(JsonNode event:events){
            Item.ItemBuilder builder = new Item.ItemBuilder();
            builder.setItemId(event.path("id").asText())
                    .setName(event.path("name").asText())
                    .setAddress(getAddress(event))
                    .setUrl(event.path("url").asText())
                    .setDistance(Double.parseDouble(event.path("distance").asText()))
                    .setCategories(getCategories(event))
                    .setImageUrl(getImageUrl(event));
            itemList.add(builder.build());
        }
        return itemList;
    }
    private String getAddress(JsonNode event){
        if (event.has("_embedded")) {
            JsonNode embedded = event.get("_embedded");
            if (embedded.has("venues")) {
                JsonNode venues = embedded.get("venues");
                for (JsonNode venue : venues) {
                    StringBuilder builder = new StringBuilder();
                    if (venue.has("address")) {
                        JsonNode address = venue.get("address");
                        if (address.has("line1")) {
                            builder.append(address.get("line1").asText());
                        }
                        if (address.has("line2")) {
                            builder.append(", ").append(address.get("line2").asText());
                        }
                        if (address.has("line3")) {
                            builder.append(", ").append(address.get("line3").asText());
                        }
                    }
                    if (venue.has("city")) {
                        JsonNode city = venue.get("city");
                        builder.append(", ").append(city.get("name").asText());
                    }
                    String result = builder.toString();
                    if (!result.isEmpty()) {
                        return result;
                    }
                }
            }
        }
        return "";
    }

    private String getImageUrl(JsonNode event){
        if (event.has("images")) {
            JsonNode array = event.get("images");
            for (JsonNode image : array) {
                if (image.has("url")) {
                    return image.get("url").asText();
                }
            }
        }
        return "";
    }
    private Set<Category> getCategories(JsonNode event){
        Set<Category> categories = new HashSet<>();
        if (event.has("classifications")) {
            JsonNode classifications = event.get("classifications");
            for (JsonNode classification : classifications) {
                if (classification.has("segment")) {
                    JsonNode segment = classification.get("segment");
                    if (segment.has("name")) {
                        categories.add(new Category(segment.get("name").asText()));
                    }
                }
            }
        }
        return categories;
    }

    public List<Item> search(double lat, double lon, String keyword) {
        if (keyword == null) {
            keyword = DEFAULT_KEYWORD;
        }
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String query = String.format("apikey=%s&latlong=%s,%s&keyword=%s&radius=%s", apiKey, lat, lon, keyword, 50);
        String url = URL + "?" + query;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Sending request to url: " + url);
            System.out.println("Response code: " + responseCode);

            if (responseCode != 200) {
                return new ArrayList<>();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonNode obj = objectMapper.readTree(response.toString());
            if (obj.has("_embedded")) {
                JsonNode embedded = obj.get("_embedded");
                return getItemList(embedded.get("events"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
