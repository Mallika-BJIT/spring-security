package com.example.security.controller;

import com.example.security.model.RequiresPermission;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @PostMapping("/book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> testMethod() {
        return ResponseEntity.ok("this method can be accessed by admin");
    }

    @GetMapping("/book")
    @RequiresPermission(resource = "book", action = "read")
    public ResponseEntity<?> getBook() {
        return ResponseEntity.ok("book read permission");
    }

    @PutMapping("/book")
    @RequiresPermission(resource = "book", action = "update")
    public ResponseEntity<?> updateUser() {
        return ResponseEntity.ok("book update permission");
    }

    @DeleteMapping("/book/{id}")
    @RequiresPermission(resource = "book", action = "delete")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok("book delete permission");
    }

    @DeleteMapping("/book")
    @RequiresPermission(resource = "book", action = "delete")
    public ResponseEntity<?> deleteUser() {
        return ResponseEntity.ok("book delete permission");
    }
}
