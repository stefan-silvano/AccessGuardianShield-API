package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.entity.User;
import com.cybersecurity.iam.exception.type.BadRequestException;
import com.cybersecurity.iam.exception.type.ConflictException;
import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.payload.request.AuthenticationRequest;
import com.cybersecurity.iam.payload.response.AuthenticationResponse;
import com.cybersecurity.iam.payload.request.RegisterRequest;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.UserRepository;
import com.cybersecurity.iam.security.auth.UserDetailsImpl;
import com.cybersecurity.iam.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        RequestValidator.validateObject(request);

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("An account with this email already exist");
        }

        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        RequestValidator.validateObject(request);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

        } catch (Exception e) {
            throw new BadRequestException("Incorrect email or password");
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        String jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
