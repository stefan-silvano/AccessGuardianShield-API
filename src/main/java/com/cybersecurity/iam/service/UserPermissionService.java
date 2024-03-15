package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.entity.Permission;
import com.cybersecurity.iam.database.entity.User;
import com.cybersecurity.iam.database.relationship.Authorization;
import com.cybersecurity.iam.database.relationship.UserPermission;
import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.utility.DateTime;
import com.cybersecurity.iam.payload.request.UserPermissionRequest;
import com.cybersecurity.iam.payload.response.UserPermissionResponse;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.AuthorizationRepository;
import com.cybersecurity.iam.repository.PermissionRepository;
import com.cybersecurity.iam.repository.UserPermissionRepository;
import com.cybersecurity.iam.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPermissionService {

    private final UserPermissionRepository userPermissionRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final AuthorizationRepository authorizationRepository;

    public UserPermissionResponse createUserPermission(UserPermissionRequest request) {
        RequestValidator.validateObject(request);
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User with id " + request.getUserId() + " not found"));
        Permission permission = permissionRepository.findById(request.getPermissionId())
                .orElseThrow(() -> new NotFoundException("Permission with id " + request.getPermissionId() + " not found"));

        UserPermission userPermission = UserPermission.builder()
                .user(user)
                .permission(permission)
                .startDate(DateTime.toTimestamp(request.getStartDate()))
                .endDate(DateTime.toTimestamp(request.getEndDate()))
                .description(request.getDescription())
                .build();

        userPermission = userPermissionRepository.save(userPermission);
        Authorization authorization = Authorization.builder()
                .startDate(userPermission.getStartDate())
                .endDate(userPermission.getEndDate())
                .permission(permission)
                .user(user)
                .userPermission(userPermission)
                .build();
        authorizationRepository.save(authorization);

        return UserPermissionResponse.builder()
                .id(userPermission.getId())
                .user(userPermission.getUser())
                .permission(userPermission.getPermission())
                .startDate(DateTime.toString(userPermission.getStartDate()))
                .endDate(DateTime.toString(userPermission.getEndDate()))
                .description(userPermission.getDescription())
                .build();
    }

    public UserPermissionResponse getUserPermission(Integer id) {
        UserPermission userPermission = userPermissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User-permission with id: " + id + " not found"));
        return UserPermissionResponse.builder()
                .id(userPermission.getId())
                .user(userPermission.getUser())
                .permission(userPermission.getPermission())
                .startDate(DateTime.toString(userPermission.getStartDate()))
                .endDate(DateTime.toString(userPermission.getEndDate()))
                .description(userPermission.getDescription())
                .build();
    }

    public GenericResponse deleteUserPermission(Integer id) {
        GenericResponse response;
        if (!userPermissionRepository.existsById(id)) {
            throw new NotFoundException("User-permission with id: " + id + " not found");
        }
        response = new GenericResponse(userPermissionRepository.findById(id), "User-permission with id: " + id
                + " successfully deleted", HttpServletResponse.SC_OK);
        List<Authorization> authorizations = authorizationRepository.findAllByUserPermissionId(id);
        authorizationRepository.deleteAll(authorizations);
        userPermissionRepository.deleteById(id);
        return response;
    }

    public List<GenericResponse> createUserPermissions(List<UserPermissionRequest> request) {
        List<GenericResponse> response = new ArrayList<>();
        for (UserPermissionRequest req : request) {
            if (RequestValidator.isObjectValid(req) != null) {
                response.add(new GenericResponse(req, RequestValidator.isObjectValid(req), HttpServletResponse.SC_BAD_REQUEST));
            } else {
                User user = userRepository.findById(req.getUserId()).orElse(null);
                Permission permission = permissionRepository.findById(req.getPermissionId()).orElse(null);
                if (user == null) {
                    response.add(new GenericResponse(req, "The userId " + req.getUserId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
                if (permission == null) {
                    response.add(new GenericResponse(req, "The permissionId " + req.getPermissionId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
                if (user != null && permission != null) {
                    UserPermission userPermission = UserPermission.builder()
                            .user(user)
                            .permission(permission)
                            .startDate(DateTime.toTimestamp(req.getStartDate()))
                            .endDate(DateTime.toTimestamp(req.getEndDate()))
                            .description(req.getDescription())
                            .build();

                    userPermission = userPermissionRepository.save(userPermission);
                    Authorization authorization = Authorization.builder()
                            .startDate(userPermission.getStartDate())
                            .endDate(userPermission.getEndDate())
                            .permission(permission)
                            .user(user)
                            .userPermission(userPermission)
                            .build();
                    authorizationRepository.save(authorization);
                    response.add(new GenericResponse(userPermission, "User successfully created", HttpServletResponse.SC_CREATED));
                }
            }
        }
        return response;
    }

    public List<UserPermissionResponse> getUserPermissions() {
        List<UserPermission> userPermissions = userPermissionRepository.findAll();
        if (userPermissions.isEmpty()) {
            throw new NotFoundException("No user-permission was found");
        }

        List<UserPermissionResponse> userPermissionResponses = new ArrayList<>();
        for (UserPermission userPermission : userPermissions) {
            userPermissionResponses.add(UserPermissionResponse.builder()
                    .id(userPermission.getId())
                    .user(userPermission.getUser())
                    .permission(userPermission.getPermission())
                    .startDate(DateTime.toString(userPermission.getStartDate()))
                    .endDate(DateTime.toString(userPermission.getEndDate()))
                    .description(userPermission.getDescription())
                    .build()
            );
        }
        return userPermissionResponses;
    }

    public List<GenericResponse> deleteUserPermissions(List<Integer> ids) {
        List<GenericResponse> response = new ArrayList<>();
        for (Integer id : ids) {
            if (userPermissionRepository.existsById(id)) {
                response.add(new GenericResponse(userPermissionRepository.findById(id), "User-permission with id: " + id + " successfully deleted", HttpServletResponse.SC_OK));
                List<Authorization> authorizations = authorizationRepository.findAllByUserPermissionId(id);
                authorizationRepository.deleteAll(authorizations);
                userPermissionRepository.deleteById(id);
            } else {
                response.add(new GenericResponse(null, "User-permission with id: " + id + " not found", HttpServletResponse.SC_NOT_FOUND));
            }
        }
        return response;
    }
}
