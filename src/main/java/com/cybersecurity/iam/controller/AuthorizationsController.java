package com.cybersecurity.iam.controller;

import com.cybersecurity.iam.payload.response.AddressResponse;
import com.cybersecurity.iam.payload.response.AuthorizationResponse;
import com.cybersecurity.iam.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/iam-api/v1/authorizations")
public class AuthorizationsController {

    private final AuthorizationService authorizationService;
    @GetMapping()
    public ResponseEntity<List<AuthorizationResponse>> getAuthorizations() {
        return ResponseEntity.ok(authorizationService.getAuthorizations());
    }
}
