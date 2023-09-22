package com.example.salineroyaleacademy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.salineroyaleacademy.user.User;
import com.example.salineroyaleacademy.user.UserRepository;

// Insert fake data into the database.
@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        User user1 = new User();
        user1.setUsername("justin-katasi");
        user1.setEmail("justinkatasi@gmail.com");
        user1.setPassword("password");
        userRepository.save(user1);
    }
}
