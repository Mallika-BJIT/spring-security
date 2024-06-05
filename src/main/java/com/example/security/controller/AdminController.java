package com.example.security.controller;

import com.example.security.model.RequiresPermission;
import com.example.security.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final TestService testService;

    @PostMapping("/admin")
    @RequiresPermission(resource = "admin", action = "create")
    public ResponseEntity<?> createMethod() {
        return ResponseEntity.ok("admin_create access");
    }

    @GetMapping("/admin/{id}")
    @RequiresPermission(resource = "admin", action = "read")
    public ResponseEntity<?> getMethod(@PathVariable Long id) {
        return ResponseEntity.ok(testService.getUser(id));
    }

    @PutMapping("/admin")
    @RequiresPermission(resource = "admin", action = "update")
    public ResponseEntity<?> updateMethod() {
        return ResponseEntity.ok("admin_update access");
    }

    @DeleteMapping("/admin")
    @RequiresPermission(resource = "admin", action = "delete")
    public ResponseEntity<?> deleteMethod() {
        return ResponseEntity.ok("admin_delete access");
    }
}
