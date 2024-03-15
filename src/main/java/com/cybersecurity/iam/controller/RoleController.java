package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.request.RoleRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.MessageResponse;
import com.cybersecurity.iam.payload.response.RoleResponse;
import com.cybersecurity.iam.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iam-api/v1/roles")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping()
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createOrganization(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRole(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.getRole(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoleResponse> patchRole(@PathVariable Integer id, @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.patchRole(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteRole(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.deleteRole(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> createRoles(@RequestBody List<RoleRequest> request) {
        return ResponseEntity.ok(roleService.createRoles(request));
    }

    @GetMapping()
    public ResponseEntity<List<RoleResponse>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> deleteRoles(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(roleService.deleteRoles(ids));
    }
}
