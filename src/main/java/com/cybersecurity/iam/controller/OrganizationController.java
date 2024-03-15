package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.request.OrganizationRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.MessageResponse;
import com.cybersecurity.iam.payload.response.OrganizationResponse;
import com.cybersecurity.iam.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/iam-api/v1/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping()
    public ResponseEntity<OrganizationResponse> createOrganization(@RequestBody OrganizationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.createOrganization(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> getOrganization(@PathVariable Integer id) {
        return ResponseEntity.ok(organizationService.getOrganization(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrganizationResponse> patchOrganization(@PathVariable Integer id, @RequestBody OrganizationRequest request) {
        return ResponseEntity.ok(organizationService.patchOrganization(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteOrganization(@PathVariable Integer id) {
        return ResponseEntity.ok(organizationService.deleteOrganization(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> createOrganizations(@RequestBody List<OrganizationRequest> request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.createOrganizations(request));
    }

    @GetMapping()
    public ResponseEntity<List<OrganizationResponse>> getOrganizations() {
        return ResponseEntity.ok(organizationService.getOrganizations());
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> deleteOrganizations(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(organizationService.deleteOrganizations(ids));
    }
}
