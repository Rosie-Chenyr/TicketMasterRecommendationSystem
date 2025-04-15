package org.example.liba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
//@EnableJpaRepositories(basePackages = "org.example.liba.repository")
//@EntityScan(basePackages = "org.example.liba.model")
public class LibaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibaApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
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
