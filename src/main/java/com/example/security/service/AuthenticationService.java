package com.example.security.service;

import com.example.security.auth.JWTUtils;
import com.example.security.controller.UserController;
import com.example.security.exception.CustomException;
import com.example.security.model.*;
import com.example.security.repositories.FeatureRepository;
import com.example.security.repositories.PermissionRepository;
import com.example.security.repositories.UserFeaturePermissionRepository;
import com.example.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PermissionRepository permissionRepository;
    private Logger log = LoggerFactory.getLogger(UserController.class);
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final FeatureRepository featureRepository;


    public String login(AuthenticationDTO authenticationDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationDTO.getEmail(), authenticationDTO.getPassword()
            ));
            //check if the user exist

            return jwtUtils.generateToken(userRepository.findByEmail(
                    authenticationDTO.getEmail()).get());
        } catch (BadCredentialsException ex) {
            log.error("Bad credential exception {}", ex.getMessage());
            throw ex;
        }
    }

    public String register(User user) {
        if (!ObjectUtils.isEmpty(userRepository.findByEmail(user.getEmail()))) {
            throw new CustomException("User already exists", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (!ObjectUtils.isEmpty(user.getUserFeaturePermissions())) {
            List<UserFeaturePermission> userFeaturePermissionList = new ArrayList<>();

            for (UserFeaturePermission userFeaturePermission : user.getUserFeaturePermissions()) {
                Optional<Feature> feature = featureRepository.findByFeatureName(
                        userFeaturePermission.getFeature().getFeatureName());

                Optional<Permission> permission = permissionRepository.findByPermissionName(
                        userFeaturePermission.getPermission().getPermissionName());

                if (feature.isEmpty() || permission.isEmpty()) {
                    // log.error("{} not exists", userFeaturePermission.getFeature().getFeatureName());
                    throw new CustomException("user registration failed", HttpStatus.BAD_REQUEST);
                }
                //permission check
                userFeaturePermission.setId(new UserFeaturePermissionId());
                userFeaturePermission.setFeature(feature.get());
                userFeaturePermission.setPermission(permission.get());
                userFeaturePermission.setUser(user);
            }
        }
        userRepository.save(user);

        return jwtUtils.generateToken(user);
    }
}
