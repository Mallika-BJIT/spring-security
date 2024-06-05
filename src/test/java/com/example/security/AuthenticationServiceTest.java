package com.example.security;

import com.example.security.service.UserPermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class AuthenticationServiceTest {
    @Autowired
    UserPermissionService userPermissionService;

    @WithMockUser(username = "dummyuser")
    @Test
    public void testCallUserFailed() {
        assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(
                () -> this.userPermissionService.callUser(12L));
    }
}
