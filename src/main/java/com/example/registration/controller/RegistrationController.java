package com.example.registration.controller;

import com.example.registration.exception.ResourceNotFoundException;
import com.example.registration.model.User;
import com.example.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllNotes() {
        return userRepository.findAll();
    }

    @PostMapping("/add")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @PutMapping("/users/{id}")
    public User updateNote(@PathVariable(value = "id") Long userId,
                           @Valid @RequestBody User userDetails) {

        User note = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userDetails.setFirstname(userDetails.getFirstname());
        userDetails.setLastname(userDetails.getLastname());
        userDetails.setEmail(userDetails.getEmail());
        userDetails.setPostcode(userDetails.getPostcode());

        User updatedUser = userRepository.save(note);
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
