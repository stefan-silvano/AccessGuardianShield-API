package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.entity.User;
import com.cybersecurity.iam.database.relationship.Authorization;
import com.cybersecurity.iam.exception.type.ConflictException;
import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.payload.request.UserRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.UserResponse;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.AuthorizationRepository;
import com.cybersecurity.iam.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorizationRepository authorizationRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request) {
        RequestValidator.validateObject(request);
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("An account with this email already exist");
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public UserResponse getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public UserResponse patchUser(Integer id, UserRequest request) {
        RequestValidator.validateObject(request);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        String password = request.getPassword().isEmpty()
                ? user.getPassword() : passwordEncoder.encode(request.getPassword());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(password);
        user = userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public GenericResponse deleteUser(Integer id) {
        GenericResponse response;
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id: " + id + " not found");
        }
        response = new GenericResponse(userRepository.findById(id), "User with id: " + id
                + " successfully deleted", HttpServletResponse.SC_OK);
        List<Authorization> authorizations = authorizationRepository.findAllByUserId(id);
        authorizationRepository.deleteAll(authorizations);
        userRepository.deleteById(id);
        return response;
    }

    public List<GenericResponse> createUsers(List<UserRequest> request) {
        List<GenericResponse> response = new ArrayList<>();
        for (UserRequest req : request) {
            if (RequestValidator.isObjectValid(req) != null) {
                response.add(new GenericResponse(req, RequestValidator.isObjectValid(req), HttpServletResponse.SC_BAD_REQUEST));
            } else {
                if (userRepository.existsByEmail(req.getEmail())) {
                    response.add(new GenericResponse(req, "An account with this email already exist", HttpServletResponse.SC_CONFLICT));
                } else {
                    User user = User.builder()
                            .firstName(req.getFirstName())
                            .lastName(req.getLastName())
                            .email(req.getEmail())
                            .password(passwordEncoder.encode(req.getPassword()))
                            .build();
                    userRepository.save(user);
                    response.add(new GenericResponse(req, "User successfully created", HttpServletResponse.SC_CREATED));
                }
            }
        }
        return response;
    }

    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("No user was found");
        }

        List<UserResponse> usersResponse = new ArrayList<>();
        for (User user : users) {
            usersResponse.add(UserResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build()
            );
        }
        return usersResponse;
    }

    public List<GenericResponse> deleteUsers(List<Integer> ids) {
        List<GenericResponse> response = new ArrayList<>();
        for (Integer id : ids) {
            if (userRepository.existsById(id)) {
                response.add(new GenericResponse(userRepository.findById(id), "User with id: " + id + " successfully deleted", HttpServletResponse.SC_OK));
                List<Authorization> authorizations = authorizationRepository.findAllByUserId(id);
                authorizationRepository.deleteAll(authorizations);
                userRepository.deleteById(id);
            } else {
                response.add(new GenericResponse(null, "User with id: " + id + " not found", HttpServletResponse.SC_NOT_FOUND));
            }
        }
        return response;
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
