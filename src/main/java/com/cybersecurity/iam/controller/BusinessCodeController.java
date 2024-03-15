package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.request.BusinessCodeRequest;
import com.cybersecurity.iam.payload.response.BusinessCodeResponse;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.MessageResponse;
import com.cybersecurity.iam.service.BusinessCodeService;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/iam-api/v1/business-codes")
public class BusinessCodeController {

    private final BusinessCodeService businessCodeService;

    @PostMapping()
    public ResponseEntity<BusinessCodeResponse> createBusinessCode(@RequestBody BusinessCodeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(businessCodeService.createBusinessCode(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessCodeResponse> getBusinessCode(@PathVariable Integer id) {
        return ResponseEntity.ok(businessCodeService.getBusinessCode(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BusinessCodeResponse> patchBusinessCode(@PathVariable Integer id, @RequestBody BusinessCodeRequest request) {
        return ResponseEntity.ok(businessCodeService.patchBusinessCode(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteBusinessCode(@PathVariable Integer id) {
        return ResponseEntity.ok(businessCodeService.deleteBusinessCode(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> createBusinessCodes(@RequestBody List<BusinessCodeRequest> request) {
        return ResponseEntity.ok(businessCodeService.createBusinessCodes(request));
    }

    @GetMapping()
    public ResponseEntity<List<BusinessCodeResponse>> getBusinessCodes() {
        return ResponseEntity.ok(businessCodeService.getBusinessCodes());
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<GenericResponse>> deleteBusinessCodes(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(businessCodeService.deleteBusinessCodes(ids));
    }
}
