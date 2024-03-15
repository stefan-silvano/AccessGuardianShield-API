package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.request.RolePermissionRequest;
import com.cybersecurity.iam.payload.request.UserPermissionRequest;
import com.cybersecurity.iam.payload.request.UserRequest;
import com.cybersecurity.iam.payload.response.*;
import com.cybersecurity.iam.service.UserPermissionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iam-api/v1/user-permission")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserPermissionController {

    private final UserPermissionService userPermissionService;

    @PostMapping()
    public ResponseEntity<UserPermissionResponse> createUserPermission(@RequestBody UserPermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userPermissionService.createUserPermission(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPermissionResponse> getUserPermission(@PathVariable Integer id){
        return ResponseEntity.ok(userPermissionService.getUserPermission(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteUserPermission(@PathVariable Integer id) {
        return ResponseEntity.ok(userPermissionService.deleteUserPermission(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> createUserPermissions(@RequestBody List<UserPermissionRequest> request) {
        return ResponseEntity.ok(userPermissionService.createUserPermissions(request));
    }

    @GetMapping()
    public ResponseEntity<List<UserPermissionResponse>> getUserPermissions() {
        return ResponseEntity.ok(userPermissionService.getUserPermissions());
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> deleteUserPermissions(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(userPermissionService.deleteUserPermissions(ids));
    }
}
