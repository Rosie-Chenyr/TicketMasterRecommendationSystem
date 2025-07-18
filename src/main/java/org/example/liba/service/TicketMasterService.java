package org.example.liba.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.liba.entity.Category;
import org.example.liba.entity.Item;
import org.example.liba.repository.CategoryRepository;
import org.example.liba.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDateTime;
import java.util.*;

@PropertySource("classpath:application.properties")
@Service
public class TicketMasterService {
    private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
    private static final String DEFAULT_KEYWORD = "";
    public static final int DEFAULT_RADIUS = 50;

    @Value("${ticketmaster.api.key}")
    private String apiKey;

    @Value("${ticketmaster.url}")
    private String ticketMasterURL;

    @Autowired
    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TicketMasterService(
            ObjectMapper objectMapper, ObjectMapper objectMapper1, RestTemplate restTemplate,
            ItemRepository itemRepository,
            CategoryRepository categoryRepository) {
        this.objectMapper = objectMapper1;
        this.restTemplate = restTemplate;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Item> search(double lat, double lon, String keyword, int radius) {
        if (keyword == null) {
            keyword = DEFAULT_KEYWORD;
        }

        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String query = String.format("apikey=%s&latlong=%s,%s&keyword=%s&radius=%s", apiKey, lat, lon,
                keyword, radius);
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
                List<Item> itemList = getItemList(embedded.get("events"));
                List<Item> updatedItemList = new ArrayList<>();

                for (Item item : itemList) {
                    // Ensure categories are saved first
                    Set<Category> savedCategories = new HashSet<>();
                    for (Category category : item.getCategories()) {
                        // Check if the category already exists in the database
                        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
                        if (existingCategory.isPresent()) {
                            savedCategories.add(existingCategory.get());
                        } else {
                            // Save the new category and add it to the set
                            Category savedCategory = categoryRepository.save(category);
                            savedCategories.add(savedCategory);
                        }
                    }

                    // Set the saved categories and id back to the item
                    item.setCategories(savedCategories);
                    item.setId(UUID.randomUUID().toString());

                    // Add the item to the list
                    updatedItemList.add(item);
                }

                return updatedItemList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }



            private List<Item> getItemList(JsonNode events){
        List<Item> itemList = new ArrayList<>();
        for(JsonNode event:events){
            Item.ItemBuilder builder = new Item.ItemBuilder();
            builder.setId(event.path("id").asText())
                    .setName(event.path("name").asText())
                    .setDateTime(getDateTime(event))
                    .setPrice(getPrice(event))
                    .setAddress(getAddress(event))
                    .setUrl(event.path("url").asText())
                    .setDistance(Double.parseDouble(event.path("distance").asText()))
                    .setCategories(getCategories(event))
                    .setImageUrl(getImageUrl(event));
            itemList.add(builder.build());
        }
        return itemList;
    }

    private LocalDateTime getDateTime(JsonNode event) {
        if (event.has("dates") && event.get("dates").has("start")) {
            JsonNode startDate = event.get("dates").get("start");
            if (startDate.has("localDate")) {
                String dateStr = startDate.get("localDate").asText();
                String timeStr = startDate.has("localTime") ?
                        startDate.get("localTime").asText() : "00:00";

                try {
                    LocalDateTime eventDateTime = LocalDateTime.parse(
                            dateStr + "T" + timeStr
                    );
                    return eventDateTime;
                } catch (Exception e) {
                    System.err.println("Error parsing date: " + dateStr + " " + timeStr);
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private double getPrice(JsonNode event) {
        if (event.has("priceRanges") && event.get("priceRanges").size() > 0) {
            JsonNode priceRange = event.get("priceRanges").get(0);
            if (priceRange.has("min")) {
                double price = priceRange.get("min").asDouble();
                return price;
            }
        }
        return 0;
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


    public List<Item> searchByLatLon(double lat, double lon, String keyword, int radius) {
        return search(lat, lon, keyword, radius);
    }

    // 可选：带默认 radius 的版本
    public List<Item> searchByLatLon(double lat, double lon, String keyword) {
        return search(lat, lon, keyword, DEFAULT_RADIUS); // ✅ 使用 search，不会死循环
    }

}

