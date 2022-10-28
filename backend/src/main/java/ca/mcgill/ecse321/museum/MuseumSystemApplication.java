package ca.mcgill.ecse321.museum;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ca.mcgill.ecse321.museum.model.MuseumSystem;
import ca.mcgill.ecse321.museum.repository.MuseumSystemRepository;

@SpringBootApplication
public class MuseumSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuseumSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MuseumSystemRepository museumSystemRepository) {
        return args -> {

        };
    }

}
