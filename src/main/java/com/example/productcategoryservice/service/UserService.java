package com.example.productcategoryservice.service;

import com.example.productcategoryservice.entity.User;
import com.example.productcategoryservice.exception.ApiError;
import com.example.productcategoryservice.repository.UserRepository;
import com.example.productcategoryservice.security.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailServiceImpl userDetailService;

    public User saveUser(User user) throws ApiError {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new ApiError(HttpStatus.CONFLICT, 409, LocalDateTime.now(), "user has been created.");
        }
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
