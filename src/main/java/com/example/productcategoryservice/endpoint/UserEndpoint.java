package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.*;
import com.example.productcategoryservice.entity.User;
import com.example.productcategoryservice.exception.ApiError;
import com.example.productcategoryservice.mapper.UserMapper;
import com.example.productcategoryservice.repository.CustomUserRepository;
import com.example.productcategoryservice.security.CurrentUser;
import com.example.productcategoryservice.service.UserService;
import com.example.productcategoryservice.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserEndpoint {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;
    private  final CustomUserRepository customUserRepository;


    @GetMapping
    public List<UserDto> getUsers(@AuthenticationPrincipal CurrentUser currentUser,
                                                    @RequestBody UserFilterDto userFilterDto){
        List<UserDto> result = new ArrayList<>();
        for (User user : customUserRepository.users(userFilterDto)) {
            result.add(userMapper.map(user));
        }
        return result;
    }


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


