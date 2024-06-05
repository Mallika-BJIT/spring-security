package com.example.security.service;

import com.example.security.exception.CustomException;
import com.example.security.model.Role;
import com.example.security.dto.request.RoleAssignDTO;
import com.example.security.model.User;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPermissionService {
    private Logger log = LoggerFactory.getLogger(UserPermissionService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public String updatePermission(RoleAssignDTO roleDTO) {
        return roleDTO.getAdd() ?
                addPermission(roleDTO) : revokePermission(roleDTO);
    }

    private String revokePermission(RoleAssignDTO roleDTO) {
        User user = userRepository.findById(roleDTO.getUserId()).orElseThrow(() ->
                new CustomException("user not exist", HttpStatus.BAD_REQUEST));

        Role role = roleRepository.findByRoleName(roleDTO.getRoleName()).orElseThrow(
                () -> new CustomException("Role doesn't exist", HttpStatus.BAD_REQUEST));

        user.getRoles().remove(role);
        userRepository.save(user);

        return "permission revoke successful";
    }

    private String addPermission(RoleAssignDTO roleDTO) {
        Role role = roleRepository.findByRoleName(roleDTO.getRoleName()).orElseThrow(
                () -> new CustomException("Role doesn't exist", HttpStatus.BAD_REQUEST));

        Optional<User> optionalUser = userRepository.findById(roleDTO.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.getRoles().add(role);
            userRepository.save(user);
        } else {
            throw new CustomException("user not exist", HttpStatus.BAD_REQUEST);
        }
        return "Role assigned successfully";
    }

    /*public String createPermission(PermissionDTO permissionDTO) {
        permissionRepository.save(Permission
                .builder()
                .permissionName(permissionDTO.getPermissionName())
                .build());
        return "permission added";
    }*/
}
