package com.example.salineroyaleacademy.user;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.salineroyaleacademy.exception.UserNotFoundException;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() throws UserNotFoundException {
        try {
            return userRepository.findAll();
        } catch (Exception ex) {
            throw new UserNotFoundException("Error fetching all users.");
        }
    }

    public User getUser(int id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
    
        return userOptional.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
    
}