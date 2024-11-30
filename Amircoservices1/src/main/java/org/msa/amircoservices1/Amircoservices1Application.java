package org.msa.amircoservices1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Amircoservices1Application {

    public static void main(String[] args) {
        SpringApplication.run(Amircoservices1Application.class, args);
    }
    @RestController
    class ms1TestControl{
        @GetMapping("api/test/message")
        public String displayMsg(){
            return "this is message from ms1";
        }
    }

}
