package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.entity.Container;
import com.cybersecurity.iam.database.entity.Role;
import com.cybersecurity.iam.database.relationship.Authorization;
import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.payload.request.RoleRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.RoleResponse;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.AuthorizationRepository;
import com.cybersecurity.iam.repository.ContainerRepository;
import com.cybersecurity.iam.repository.RoleRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final AuthorizationRepository authorizationRepository;
    private final ContainerRepository containerRepository;

    public RoleResponse createOrganization(RoleRequest request) {
        RequestValidator.validateObject(request);
        Container container = containerRepository.findById(request.getContainerId())
                .orElseThrow(() -> new NotFoundException("Container with id: " + request.getContainerId() + " not found"));
        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .riskLevel(request.getRiskLevel())
                .container(container)
                .build();
        role = roleRepository.save(role);
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .type(role.getType())
                .riskLevel(role.getRiskLevel())
                .container(role.getContainer())
                .build();
    }

    public RoleResponse getRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role with id: " + id + " not found"));
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .type(role.getType())
                .riskLevel(role.getRiskLevel())
                .container(role.getContainer())
                .build();
    }

    public RoleResponse patchRole(Integer id, RoleRequest request) {
        RequestValidator.validateObject(request);
        Container container = containerRepository.findById(request.getContainerId())
                .orElseThrow(() -> new NotFoundException("Container with id: " + request.getContainerId() + " not found"));
        Role role = Role.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .riskLevel(request.getRiskLevel())
                .container(container)
                .build();
        role = roleRepository.save(role);
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .type(role.getType())
                .riskLevel(role.getRiskLevel())
                .container(role.getContainer())
                .build();

    }

    public GenericResponse deleteRole(Integer id) {
        GenericResponse response;
        if (!roleRepository.existsById(id)) {
            throw new NotFoundException("Role with id: " + id + " not found");
        }
        response = new GenericResponse(roleRepository.findById(id), "Role with id: " + id
                + " successfully deleted", HttpServletResponse.SC_OK);
        List<Authorization> authorizations = authorizationRepository.findAllByRoleId(id);
        authorizationRepository.deleteAll(authorizations);
        roleRepository.deleteById(id);
        return response;
    }

    public List<GenericResponse> createRoles(List<RoleRequest> request) {
        List<GenericResponse> response = new ArrayList<>();
        for (RoleRequest req : request) {
            if (RequestValidator.isObjectValid(req) != null) {
                response.add(new GenericResponse(req, RequestValidator.isObjectValid(req), HttpServletResponse.SC_BAD_REQUEST));
            } else {
                Container container = containerRepository.findById(req.getContainerId()).orElse(null);
                if (container != null) {
                    Role role = Role.builder()
                            .name(req.getName())
                            .description(req.getDescription())
                            .type(req.getType())
                            .riskLevel(req.getRiskLevel())
                            .container(container)
                            .build();
                    roleRepository.save(role);
                    response.add(new GenericResponse(req, "Role successfully created", HttpServletResponse.SC_CREATED));
                } else {
                    response.add(new GenericResponse(req, "The containerId " + req.getContainerId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
            }
        }
        return response;
    }

    public List<RoleResponse> getRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            throw new NotFoundException("No role was found");
        }

        List<RoleResponse> rolesResponse = new ArrayList<>();
        for (Role role : roles) {
            rolesResponse.add(RoleResponse.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .description(role.getDescription())
                    .type(role.getType())
                    .riskLevel(role.getRiskLevel())
                    .container(role.getContainer())
                    .build()
            );
        }
        return rolesResponse;
    }

    public List<GenericResponse> deleteRoles(List<Integer> ids) {
        List<GenericResponse> response = new ArrayList<>();
        for (Integer id : ids) {
            if (roleRepository.existsById(id)) {
                response.add(new GenericResponse(roleRepository.findById(id), "Role with id: " + id + " successfully deleted", HttpServletResponse.SC_OK));
                List<Authorization> authorizations = authorizationRepository.findAllByRoleId(id);
                authorizationRepository.deleteAll(authorizations);
                roleRepository.deleteById(id);
            } else {
                response.add(new GenericResponse(null, "Role with id: " + id + " not found", HttpServletResponse.SC_NOT_FOUND));
            }
        }
        return response;
    }
}
