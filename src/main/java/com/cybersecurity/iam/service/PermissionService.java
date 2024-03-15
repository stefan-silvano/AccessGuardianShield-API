package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.entity.Container;
import com.cybersecurity.iam.database.entity.Permission;
import com.cybersecurity.iam.database.relationship.Authorization;
import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.payload.request.PermissionRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.PermissionResponse;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.AuthorizationRepository;
import com.cybersecurity.iam.repository.ContainerRepository;
import com.cybersecurity.iam.repository.PermissionRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final AuthorizationRepository authorizationRepository;
    private final ContainerRepository containerRepository;

    public PermissionResponse createPermission(PermissionRequest request) {
        RequestValidator.validateObject(request);
        Container container = containerRepository.findById(request.getContainerId())
                .orElseThrow(() -> new NotFoundException("Container with id: " + request.getContainerId() + " not found"));
        Permission permission = Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .riskLevel(request.getRiskLevel())
                .container(container)
                .build();
        permission = permissionRepository.save(permission);
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .riskLevel(permission.getRiskLevel())
                .container(permission.getContainer())
                .build();
    }

    public PermissionResponse getPermission(Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permission with id: " + id + " not found"));
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .riskLevel(permission.getRiskLevel())
                .container(permission.getContainer())
                .build();
    }

    public PermissionResponse patchPermission(Integer id, PermissionRequest request) {
        RequestValidator.validateObject(request);
        Container container = containerRepository.findById(request.getContainerId())
                .orElseThrow(() -> new NotFoundException("Container with id: " + request.getContainerId() + " not found"));
        Permission permission = Permission.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .riskLevel(request.getRiskLevel())
                .container(container)
                .build();
        permission = permissionRepository.save(permission);
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .riskLevel(permission.getRiskLevel())
                .container(permission.getContainer())
                .build();
    }

    public GenericResponse deletePermission(Integer id) {
        GenericResponse response;
        if (!permissionRepository.existsById(id)) {
            throw new NotFoundException("Permission with id: " + id + " not found");
        }
        response = new GenericResponse(permissionRepository.findById(id), "Permission with id: " + id
                + " successfully deleted", HttpServletResponse.SC_OK);
        List<Authorization> authorizations = authorizationRepository.findAllByPermissionId(id);
        authorizationRepository.deleteAll(authorizations);
        permissionRepository.deleteById(id);
        return response;
    }

    public List<GenericResponse> createPermissions(List<PermissionRequest> request) {
        List<GenericResponse> response = new ArrayList<>();
        for (PermissionRequest req : request) {
            if (RequestValidator.isObjectValid(req) != null) {
                response.add(new GenericResponse(req, RequestValidator.isObjectValid(req), HttpServletResponse.SC_BAD_REQUEST));
            } else {
                Container container = containerRepository.findById(req.getContainerId()).orElse(null);
                if (container != null) {
                    Permission permission = Permission.builder()
                            .name(req.getName())
                            .description(req.getDescription())
                            .riskLevel(req.getRiskLevel())
                            .container(container)
                            .build();
                    permissionRepository.save(permission);
                    response.add(new GenericResponse(req, "Permission successfully created", HttpServletResponse.SC_CREATED));
                } else {
                    response.add(new GenericResponse(req, "The containerId " + req.getContainerId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
            }
        }
        return response;
    }

    public List<PermissionResponse> getPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        if (permissions.isEmpty()) {
            throw new NotFoundException("No permission was found");
        }

        List<PermissionResponse> permissionsResponse = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionsResponse.add(PermissionResponse.builder()
                    .id(permission.getId())
                    .name(permission.getName())
                    .description(permission.getDescription())
                    .riskLevel(permission.getRiskLevel())
                    .container(permission.getContainer())
                    .build()
            );
        }
        return permissionsResponse;
    }

    public List<GenericResponse> deletePermissions(List<Integer> ids) {
        List<GenericResponse> response = new ArrayList<>();
        for (Integer id : ids) {
            if (permissionRepository.existsById(id)) {
                response.add(new GenericResponse(permissionRepository.findById(id), "Permission with id: " + id + " successfully deleted", HttpServletResponse.SC_OK));
                List<Authorization> authorizations = authorizationRepository.findAllByPermissionId(id);
                authorizationRepository.deleteAll(authorizations);
                permissionRepository.deleteById(id);
            } else {
                response.add(new GenericResponse(null, "Permission with id: " + id + " not found", HttpServletResponse.SC_NOT_FOUND));
            }
        }
        return response;
    }
}
