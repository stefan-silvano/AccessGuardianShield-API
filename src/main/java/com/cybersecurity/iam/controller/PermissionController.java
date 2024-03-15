package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.request.PermissionRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.MessageResponse;
import com.cybersecurity.iam.payload.response.PermissionResponse;
import com.cybersecurity.iam.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iam-api/v1/permissions")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping()
    public ResponseEntity<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.createPermission(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getPermission(@PathVariable Integer id) {
        return ResponseEntity.ok(permissionService.getPermission(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PermissionResponse> patchPermission(@PathVariable Integer id, @RequestBody PermissionRequest request) {
        return ResponseEntity.ok(permissionService.patchPermission(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deletePermission(@PathVariable Integer id) {
        return ResponseEntity.ok(permissionService.deletePermission(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> createPermissions(@RequestBody List<PermissionRequest> request) {
        return ResponseEntity.ok(permissionService.createPermissions(request));
    }

    @GetMapping()
    public ResponseEntity<List<PermissionResponse>> getPermissions() {
        return ResponseEntity.ok(permissionService.getPermissions());
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> deleteOrganizations(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(permissionService.deletePermissions(ids));
    }
}
