package org.example.liba;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})
public class TicketMasterRecommendationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketMasterRecommendationSystemApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    //@Bean
    //public BCryptPasswordEncoder passwordEncoder() {
       // return new BCryptPasswordEncoder();
   // }
}



//package org.example.liba;
//
//
//import org.example.liba.service.TicketMasterService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.client.RestTemplate;
//
//@SpringBootApplication
//@EnableJpaRepositories("org.example.liba.repository")
//public class LibaApplication implements CommandLineRunner {
//
//    @Autowired
//    private TicketMasterService ticketMasterService;
//
//    public static void main(String[] args) {
//        SpringApplication.run(LibaApplication.class, args);
//    }
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    @Override
//    public void run(String... args) throws Exception{
//        System.out.println("Fetching events for Mountain View, CA:");
//        ticketMasterService.queryAPI(37.38, -122.08);
//
//        System.out.println("Fetching events for London, UK:");
//        ticketMasterService.queryAPI(51.503364, -0.12);
//
//        System.out.println("Fetching events for Houston, TX:");
//        ticketMasterService.queryAPI(29.682684, -95.295410);
//    }
//
//}
