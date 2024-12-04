package org.msa.amicroservices2;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
//         prevent cascading failures in a microservices architecture by providing fallback methods when a service or component becomes unavailable.
        @CircuitBreaker(name = "msa1", fallbackMethod = "getDefaultMessage")
        public String getMessageFromMs1() {
            return restTemplate.getForObject("http://localhost:9091/api/test/message", String.class);
        }

        public String getDefaultMessage(Throwable throwable) {
            return "This is a default message as the service is unavailable.";
        }
    }
}
