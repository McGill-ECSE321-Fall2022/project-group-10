/* (C)2022 */
package ca.mcgill.ecse321.museum;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.TimeZone;

@RestController
@SpringBootApplication
public class MuseumSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuseumSystemApplication.class, args);
    }

    @RequestMapping("/")
    public String greeting() {
        return "Hello world!";
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
        };
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
