package com.example.security.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAssignDTO {
    private Boolean add;

    @NotNull(message = "User id is required")
    private Long userId;

    @NotEmpty(message = "Role name is required")
    private String roleName;
}
