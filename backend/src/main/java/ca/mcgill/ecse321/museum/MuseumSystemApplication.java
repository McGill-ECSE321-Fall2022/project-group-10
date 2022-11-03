package ca.mcgill.ecse321.museum;


import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import ca.mcgill.ecse321.museum.repository.MuseumSystemRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    CommandLineRunner commandLineRunner(MuseumSystemRepository museumSystemRepository) {
        return args -> {

        };
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
