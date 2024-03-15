package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.request.UserRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.MessageResponse;
import com.cybersecurity.iam.payload.response.UserResponse;
import com.cybersecurity.iam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iam-api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> patchUser(@PathVariable Integer id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.patchUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> createUsers(@RequestBody List<UserRequest> request) {
        return ResponseEntity.ok(userService.createUsers(request));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> deleteUsers(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(userService.deleteUsers(ids));
    }
}