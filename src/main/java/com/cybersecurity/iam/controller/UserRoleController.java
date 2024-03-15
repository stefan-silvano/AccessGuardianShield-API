package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.request.UserPermissionRequest;
import com.cybersecurity.iam.payload.request.UserRoleRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.UserPermissionResponse;
import com.cybersecurity.iam.payload.response.UserRoleResponse;
import com.cybersecurity.iam.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iam-api/v1/user-role")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping()
    public ResponseEntity<UserRoleResponse> createUserRole(@RequestBody UserRoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoleService.createUserRole(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleResponse> getUserRole(@PathVariable Integer id){
        return ResponseEntity.ok(userRoleService.getUserRole(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteUserRole(@PathVariable Integer id) {
        return ResponseEntity.ok(userRoleService.deleteUserRole(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> createUserRoles(@RequestBody List<UserRoleRequest> request) {
        return ResponseEntity.ok(userRoleService.createUserRoles(request));
    }

    @GetMapping()
    public ResponseEntity<List<UserRoleResponse>> getUserRoles() {
        return ResponseEntity.ok(userRoleService.getUserRoles());
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> deleteUserRoles(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(userRoleService.deleteUserRoles(ids));
    }
}
