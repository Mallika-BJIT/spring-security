package com.example.security.service;

import com.example.security.exception.CustomException;
import com.example.security.model.*;
import com.example.security.repositories.PermissionRepository;
import com.example.security.repositories.UserFeaturePermissionRepository;
import com.example.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeaturePermissionService {
    private Logger log = LoggerFactory.getLogger(FeaturePermissionService.class);

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final UserFeaturePermissionRepository userFeaturePermissionRepository;

    @PostAuthorize("returnObject.email == authentication.name")
    public UserDTO callUser(Long id) {
        User user = userRepository.findById(id).get();
        return UserDTO
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public String updatePermission(FeaturePermissionDTO featurePermissionDTO) {
        return featurePermissionDTO.getAdd() ?
                addPermission(featurePermissionDTO) : revokePermission(featurePermissionDTO);
    }

    private String revokePermission(FeaturePermissionDTO featurePermissionDTO) {
        userFeaturePermissionRepository.deleteByFeatureAndPermissionAndUserId(
                featurePermissionDTO.getFeatureName(), featurePermissionDTO.getPermission(), featurePermissionDTO.getUserId());
        return "permission revoke successful";
    }

    private String addPermission(FeaturePermissionDTO featurePermissionDTO) {
        List<Object[]> featurePermission = userFeaturePermissionRepository.findFeaturePermissionThatNotExists(featurePermissionDTO.getUserId(),
                featurePermissionDTO.getFeatureName(), featurePermissionDTO.getPermission());

        if (ObjectUtils.isEmpty(featurePermission)) {
            throw new CustomException("permission assign failed", HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userRepository.findById(featurePermissionDTO.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.getUserFeaturePermissions().add(UserFeaturePermission.builder()
                    .id(new UserFeaturePermissionId())
                    .feature((Feature) featurePermission.get(0)[0])
                    .permission((Permission) featurePermission.get(0)[1])
                    .user(user)
                    .build());

            userRepository.save(user);
        } else {
            throw new CustomException("user not exist", HttpStatus.BAD_REQUEST);
        }
        return "permission granted";
    }

    public String createPermission(PermissionDTO permissionDTO) {
        permissionRepository.save(Permission
                .builder()
                .permissionName(permissionDTO.getPermissionName())
                .build());
        return "permission added";
    }
}
