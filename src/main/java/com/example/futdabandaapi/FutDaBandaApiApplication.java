package com.example.futdabandaapi;

import com.example.futdabandaapi.model.User;
import com.example.futdabandaapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FutDaBandaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutDaBandaApiApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            User adminUser = userRepository.findByUserRole("ADMIN");

            if (adminUser == null) {
                adminUser = new User();
                adminUser.setFullName("Helpidio Loduvino Caldeira Mateus");
                adminUser.setEmail("helpidio@gmail.com");
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode("12345");
                adminUser.setPassword(encodedPassword);
                adminUser.setConfirmPassword(encodedPassword);
                adminUser.setUserRole("ADMIN");
                userRepository.save(adminUser);
            }
        };
    }

}
