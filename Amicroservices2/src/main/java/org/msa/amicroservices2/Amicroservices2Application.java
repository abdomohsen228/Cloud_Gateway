package org.msa.amicroservices2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Amicroservices2Application {

    public static void main(String[] args) {
        SpringApplication.run(Amicroservices2Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
       return new RestTemplate();
    }
    @RestController
    class ConsumerController {
        @Autowired
        private RestTemplate restTemplate;
        @GetMapping("api/message")
        public String getMessageFromMs1() {
            final String message = restTemplate.getForObject("http://localhost:9091/api/test/message", String.class);
            return message;
        }
    }
}
