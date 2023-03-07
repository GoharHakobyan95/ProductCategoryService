package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.CreateUserDto;
import com.example.productcategoryservice.dto.UserAuthDto;
import com.example.productcategoryservice.dto.UserAuthResponseDto;
import com.example.productcategoryservice.entity.Role;
import com.example.productcategoryservice.entity.User;
import com.example.productcategoryservice.exception.ApiError;
import com.example.productcategoryservice.mapper.UserMapper;
import com.example.productcategoryservice.security.UserDetailServiceImpl;
import com.example.productcategoryservice.service.UserService;
import com.example.productcategoryservice.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserEndpoint {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody CreateUserDto createUserDto) throws ApiError {
        Optional<User> byEmail = userService.findByEmail(createUserDto.getEmail());
        try {
            if (byEmail.isEmpty()) {
                User user = userMapper.map(createUserDto);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return ResponseEntity.ok(userMapper.map(userService.saveUser(user)));
            }
        } catch (ApiError exc) {
            throw new ApiError(HttpStatus.CONFLICT, 409, LocalDateTime.now(), "User already exists");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/auth")
    public ResponseEntity<?> userAuth(@RequestBody UserAuthDto userAuthdto) {
        Optional<User> byEmail = userService.findByEmail(userAuthdto.getEmail());
        User user = byEmail.get();
        if (passwordEncoder.matches(userAuthdto.getPassword(), user.getPassword())) {
            log.info("User with username {} get auth token", user.getEmail());
            return ResponseEntity.ok(UserAuthResponseDto.builder()
                    .token(tokenUtil.generateToken(user.getEmail()))
                    .user(userMapper.map(user))
                    .build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}


