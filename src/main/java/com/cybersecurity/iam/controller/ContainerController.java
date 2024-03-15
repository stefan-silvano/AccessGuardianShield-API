package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.request.ContainerRequest;
import com.cybersecurity.iam.payload.response.ContainerResponse;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.MessageResponse;
import com.cybersecurity.iam.service.ContainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/iam-api/v1/containers")
public class ContainerController {

    private final ContainerService containerService;

    @PostMapping()
    public ResponseEntity<ContainerResponse> createContainer(@RequestBody ContainerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(containerService.createContainer(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContainerResponse> getContainer(@PathVariable Integer id) {
        return ResponseEntity.ok(containerService.getContainer(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContainerResponse> patchContainer(@PathVariable Integer id, @RequestBody ContainerRequest request) {
        return ResponseEntity.ok(containerService.patchContainer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteContainer(@PathVariable Integer id) {
        return ResponseEntity.ok(containerService.deleteContainer(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> createContainers(@RequestBody List<ContainerRequest> request) {
        return ResponseEntity.ok(containerService.createContainers(request));
    }

    @GetMapping()
    public ResponseEntity<List<ContainerResponse>> getContainers() {
        return ResponseEntity.ok(containerService.getContainers());
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> deleteContainers(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(containerService.deleteContainers(ids));
    }
}
