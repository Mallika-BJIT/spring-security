package com.example.security.controller;

import com.example.security.model.RequiresPermission;
import com.example.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LibraryController {
    private final UserRepository userRepository;

    @PostMapping("/library")
    @RequiresPermission(resource = "library", action = "create")
    public ResponseEntity<?> createLibrary() {
        return ResponseEntity.ok("create_library permission");
    }

    @GetMapping("/library")
    @RequiresPermission(resource = "library", action = "search")
    public ResponseEntity<?> getLibrary() {
        return ResponseEntity.ok("search_library permission");
    }

    @PutMapping("/library")
    @RequiresPermission(resource = "library", action = "update")
    public ResponseEntity<?> updateLibrary() {
        return ResponseEntity.ok("update_library permission");
    }

    @DeleteMapping("/library")
    @RequiresPermission(resource = "library", action = "delete")
    public ResponseEntity<?> deleteLibrary() {
        return ResponseEntity.ok("delete_library permission");
    }

    /*@GetMapping("/{id}")
    @PreAuthorize("hasRole('GUEST_USER')")
    @PostAuthorize("returnObject.body.email == authentication.name")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("user not found", HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(UserDTO.builder()
                .email(user.getEmail())
                .build());
    }*/
}
