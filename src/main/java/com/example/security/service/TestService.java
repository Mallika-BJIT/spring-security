package com.example.security.service;

import com.example.security.dto.response.UserDTO;
import com.example.security.exception.CustomException;import com.example.security.model.User;
import com.example.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class TestService {
    private final UserRepository userRepository;

    @PostAuthorize("returnObject.email == authentication.name")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("user not found", HttpStatus.NOT_FOUND));
        return UserDTO.builder()
                .email(user.getEmail())
                .build();
    }
}
