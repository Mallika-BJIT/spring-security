package com.example.security.controller;

import com.example.security.model.*;
import com.example.security.service.AuthenticationService;
import com.example.security.service.FeaturePermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    private final FeaturePermissionService featurePermissionService;
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return ResponseEntity.ok(authenticationService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationDTO authenticationDTO) {
        log.info("user {} attempt to login", authenticationDTO.getEmail());
        return ResponseEntity.ok(authenticationService.login(authenticationDTO));
    }

    @PostMapping("/permission")
    public ResponseEntity<String> createPermission(@Valid @RequestBody PermissionDTO permissionDTO) {
        return ResponseEntity.ok(featurePermissionService.createPermission(permissionDTO));
    }

    @PutMapping("/feature-permission")
    public ResponseEntity<Object> updateFeaturePermission(@RequestBody FeaturePermissionDTO featurePermissionDTO) {
        log.info("update permission of a user");
        return ResponseEntity.ok(featurePermissionService.updatePermission(featurePermissionDTO));
    }
}
