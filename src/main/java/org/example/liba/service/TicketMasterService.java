package org.example.liba.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import org.springframework.beans.factory.annotation.Value;

import org.json.JSONObject;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@PropertySource("classpath:application.properties")
@Service
public class TicketMasterService {
    //private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
    private static final String DEFAULT_KEYWORD = "";
    //private static final String API_KEY = "nYCoUhFQ3TtynWG12xOsVXqeOBPzQsHo";

    private final RestTemplate restTemplate;

    @Value("${ticketmaster.api.key}")
    private String apiKey;

    @Value("${ticketmaster.url}")
    private String ticketMasterURL;


    public TicketMasterService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    public JSONArray search(double lat, double lon, String keyword){
        if(keyword == null){
            keyword = DEFAULT_KEYWORD;
        }
        try{
            keyword = URLEncoder.encode(keyword, "UTF-8");
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ticketMasterURL)
                .queryParam("apikey", apiKey)
                .queryParam("latlong", lat + "," + lon)
                .queryParam("keyword", keyword)
                .queryParam("radius", 50);

        String url = builder.toUriString();
        System.out.println(url);

        try{
            String response = restTemplate.getForObject(url, String.class);
            JSONObject obj = new JSONObject(response);

            if(!obj.isNull("embedded")){
                JSONObject embedded = obj.getJSONObject("_embedded");
                return embedded.getJSONArray("events");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public void queryAPI(double lat, double lon){
        JSONArray events = search(lat, lon, null);
            try{
                for(int i = 0; i < events.length(); i++){
                    JSONObject event = events.getJSONObject(i);
                    System.out.println(event.toString(2));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
    }

}
