package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.entity.Organization;
import com.cybersecurity.iam.database.entity.Permission;
import com.cybersecurity.iam.database.entity.Role;
import com.cybersecurity.iam.database.entity.User;
import com.cybersecurity.iam.database.relationship.Authorization;
import com.cybersecurity.iam.database.relationship.RolePermission;
import com.cybersecurity.iam.database.relationship.UserPermission;
import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.payload.request.RolePermissionRequest;
import com.cybersecurity.iam.payload.request.UserPermissionRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.RolePermissionResponse;
import com.cybersecurity.iam.payload.response.UserPermissionResponse;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.*;
import com.cybersecurity.iam.utility.DateTime;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final OrganizationRepository organizationRepository;
    private final AuthorizationRepository authorizationRepository;

    public RolePermissionResponse createRolePermission(RolePermissionRequest request) {
        RequestValidator.validateObject(request);
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role with id: " + request.getRoleId() + " not found"));
        Permission permission = permissionRepository.findById(request.getPermissionId())
                .orElseThrow(() -> new NotFoundException("Permission with id: " + request.getPermissionId() + " not found"));
        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new NotFoundException("Organization with id: " + request.getOrganizationId() + " not found"));
        RolePermission rolePermission = RolePermission.builder()
                .role(role)
                .permission(permission)
                .organization(organization)
                .startDate(DateTime.toTimestamp(request.getStartDate()))
                .endDate(DateTime.toTimestamp(request.getEndDate()))
                .description(request.getDescription())
                .build();
        rolePermission = rolePermissionRepository.save(rolePermission);
        return RolePermissionResponse.builder()
                .id(rolePermission.getId())
                .role(rolePermission.getRole())
                .permission(rolePermission.getPermission())
                .organization(rolePermission.getOrganization())
                .startDate(DateTime.toString(rolePermission.getStartDate()))
                .endDate(DateTime.toString(rolePermission.getEndDate()))
                .description(rolePermission.getDescription())
                .build();
    }

    public RolePermissionResponse getRolePermission(Integer id) {
        RolePermission rolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role-permission with id: " + id + " not found"));
        return RolePermissionResponse.builder()
                .id(rolePermission.getId())
                .role(rolePermission.getRole())
                .permission(rolePermission.getPermission())
                .organization(rolePermission.getOrganization())
                .startDate(DateTime.toString(rolePermission.getStartDate()))
                .endDate(DateTime.toString(rolePermission.getEndDate()))
                .description(rolePermission.getDescription())
                .build();
    }

    public GenericResponse deleteRolePermission(Integer id) {
        GenericResponse response;
        if (!rolePermissionRepository.existsById(id)) {
            throw new NotFoundException("Role-permission with id: " + id + " not found");
        }
        response = new GenericResponse(rolePermissionRepository.findById(id), "Role-permission with id: " + id
                + " successfully deleted", HttpServletResponse.SC_OK);
        List<Authorization> authorizations = authorizationRepository.findAllByRolePermissionId(id);
        authorizationRepository.deleteAll(authorizations);
        rolePermissionRepository.deleteById(id);
        return response;
    }

    public List<GenericResponse> createRolePermissions(List<RolePermissionRequest> request) {
        List<GenericResponse> response = new ArrayList<>();
        for (RolePermissionRequest req : request) {
            if (RequestValidator.isObjectValid(req) != null) {
                response.add(new GenericResponse(req, RequestValidator.isObjectValid(req), HttpServletResponse.SC_BAD_REQUEST));
            } else {
                Role role = roleRepository.findById(req.getRoleId()).orElse(null);
                Permission permission = permissionRepository.findById(req.getPermissionId()).orElse(null);
                Organization organization = organizationRepository.findById(req.getOrganizationId()).orElse(null);
                if (role == null) {
                    response.add(new GenericResponse(req, "The roleId " + req.getRoleId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
                if (permission == null) {
                    response.add(new GenericResponse(req, "The permissionId " + req.getPermissionId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
                if (organization == null) {
                    response.add(new GenericResponse(req, "The organizationId " + req.getOrganizationId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
                if (role != null && permission != null && organization != null) {
                    RolePermission rolePermission = RolePermission.builder()
                            .role(role)
                            .permission(permission)
                            .organization(organization)
                            .startDate(DateTime.toTimestamp(req.getStartDate()))
                            .endDate(DateTime.toTimestamp(req.getEndDate()))
                            .description(req.getDescription())
                            .build();

                    rolePermission = rolePermissionRepository.save(rolePermission);
                    response.add(new GenericResponse(rolePermission, "Role-permission successfully created", HttpServletResponse.SC_CREATED));
                }
            }
        }
        return response;
    }

    public List<RolePermissionResponse> getRolePermissions() {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll();
        if (rolePermissions.isEmpty()) {
            throw new NotFoundException("No role-permission was found");
        }

        List<RolePermissionResponse> rolePermissionResponses = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            rolePermissionResponses.add(RolePermissionResponse.builder()
                    .id(rolePermission.getId())
                    .role(rolePermission.getRole())
                    .permission(rolePermission.getPermission())
                    .organization(rolePermission.getOrganization())
                    .startDate(DateTime.toString(rolePermission.getStartDate()))
                    .endDate(DateTime.toString(rolePermission.getEndDate()))
                    .description(rolePermission.getDescription())
                    .build()
            );
        }
        return rolePermissionResponses;
    }

    public List<GenericResponse> deleteRolePermissions(List<Integer> ids) {
        List<GenericResponse> response = new ArrayList<>();
        for (Integer id : ids) {
            if (rolePermissionRepository.existsById(id)) {
                response.add(new GenericResponse(rolePermissionRepository.findById(id), "Role-permission with id: " + id + " successfully deleted", HttpServletResponse.SC_OK));
                List<Authorization> authorizations = authorizationRepository.findAllByRolePermissionId(id);
                authorizationRepository.deleteAll(authorizations);
                rolePermissionRepository.deleteById(id);
            } else {
                response.add(new GenericResponse(null, "User-permission with id: " + id + " not found", HttpServletResponse.SC_NOT_FOUND));
            }
        }
        return response;
    }
}
