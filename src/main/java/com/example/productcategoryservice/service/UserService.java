package com.example.productcategoryservice.service;

import com.example.productcategoryservice.entity.User;
import com.example.productcategoryservice.exception.ApiError;
import com.example.productcategoryservice.exception.Error;
import com.example.productcategoryservice.exception.EntityNotFoundException;
import com.example.productcategoryservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User saveUser(User user) throws ApiError {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new EntityNotFoundException(Error.DIRECTORY_NOT_FOUND);
        }
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
