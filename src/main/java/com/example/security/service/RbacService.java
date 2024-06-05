package com.example.security.service;

import com.example.security.model.Permission;
import com.example.security.model.Role;
import com.example.security.model.User;
import com.example.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RbacService {
    @Autowired
    private UserRepository userRepository;

    public boolean hasPermission(String resource, String action) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(username).orElse(null);

        if (user == null) {
            return false;
        }

        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            List<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                String[] splitPermission = permission.getPermissionName().split("_");

                /*if (permission.getResource().equalsIgnoreCase(resource) &&
                        permission.getAction().equalsIgnoreCase(action)) {
                    return true;
                }*/

                if (resource.equalsIgnoreCase(splitPermission[0]) && action.equalsIgnoreCase(splitPermission[1])) {
                    return true;
                }
            }
        }
        return false;
    }
}
