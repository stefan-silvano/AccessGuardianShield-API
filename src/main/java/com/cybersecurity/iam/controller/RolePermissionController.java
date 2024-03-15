package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.request.RolePermissionRequest;
import com.cybersecurity.iam.payload.request.UserPermissionRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.PermissionResponse;
import com.cybersecurity.iam.payload.response.RolePermissionResponse;
import com.cybersecurity.iam.payload.response.UserPermissionResponse;
import com.cybersecurity.iam.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iam-api/v1/role-permission")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @PostMapping()
    public ResponseEntity<RolePermissionResponse> createRolePermission(@RequestBody RolePermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolePermissionService.createRolePermission(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolePermissionResponse> getRolePermission(@PathVariable Integer id) {
        return ResponseEntity.ok(rolePermissionService.getRolePermission(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteRolePermission(@PathVariable Integer id) {
        return ResponseEntity.ok(rolePermissionService.deleteRolePermission(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> createUserPermissions(@RequestBody List<RolePermissionRequest> request) {
        return ResponseEntity.ok(rolePermissionService.createRolePermissions(request));
    }

    @GetMapping()
    public ResponseEntity<List<RolePermissionResponse>> getRolePermissions() {
        return ResponseEntity.ok(rolePermissionService.getRolePermissions());
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> deleteRolePermissions(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(rolePermissionService.deleteRolePermissions(ids));
    }
}
