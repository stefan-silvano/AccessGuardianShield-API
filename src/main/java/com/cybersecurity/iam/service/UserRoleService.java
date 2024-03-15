package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.entity.Organization;
import com.cybersecurity.iam.database.entity.Permission;
import com.cybersecurity.iam.database.entity.Role;
import com.cybersecurity.iam.database.entity.User;
import com.cybersecurity.iam.database.relationship.Authorization;
import com.cybersecurity.iam.database.relationship.RolePermission;
import com.cybersecurity.iam.database.relationship.UserPermission;
import com.cybersecurity.iam.database.relationship.UserRole;
import com.cybersecurity.iam.exception.type.NotFoundException;
import com.cybersecurity.iam.payload.request.UserPermissionRequest;
import com.cybersecurity.iam.payload.request.UserRoleRequest;
import com.cybersecurity.iam.payload.response.GenericResponse;
import com.cybersecurity.iam.payload.response.UserPermissionResponse;
import com.cybersecurity.iam.payload.response.UserRoleResponse;
import com.cybersecurity.iam.payload.validator.RequestValidator;
import com.cybersecurity.iam.repository.*;
import com.cybersecurity.iam.utility.DateTime;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;

    private final AuthorizationRepository authorizationRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public UserRoleResponse createUserRole(UserRoleRequest request) {
        RequestValidator.validateObject(request);
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User with id: " + request.getUserId() + " not found"));
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role with id: " + request.getRoleId() + " not found"));
        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new NotFoundException("Role with id: " + request.getRoleId() + " not found"));

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .organization(organization)
                .startDate(DateTime.toTimestamp(request.getStartDate()))
                .endDate(DateTime.toTimestamp(request.getEndDate()))
                .description(request.getDescription())
                .build();
        userRole = userRoleRepository.save(userRole);

        List<RolePermission> listOfRolePermission = rolePermissionRepository.findAllByRoleId(role.getId());
        for (RolePermission rolePermission : listOfRolePermission) {
            Timestamp[] overlapInterval = DateTime.overleap(userRole.getStartDate(), userRole.getEndDate(), rolePermission.getStartDate(), rolePermission.getEndDate());
            if (overlapInterval != null) {
                Authorization authorization = Authorization.builder()
                        .startDate(overlapInterval[0])
                        .endDate(overlapInterval[1])
                        .user(user)
                        .role(role)
                        .permission(rolePermission.getPermission())
                        .userRole(userRole)
                        .rolePermission(rolePermission)
                        .build();
                authorizationRepository.save(authorization);
            }
        }

        return UserRoleResponse.builder()
                .id(userRole.getId())
                .user(userRole.getUser())
                .role(userRole.getRole())
                .organization(userRole.getOrganization())
                .startDate(DateTime.toString(userRole.getStartDate()))
                .endDate(DateTime.toString(userRole.getEndDate()))
                .description(userRole.getDescription())
                .build();
    }

    public UserRoleResponse getUserRole(Integer id) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User-role with id: " + id + " not found"));
        return UserRoleResponse.builder()
                .id(userRole.getId())
                .user(userRole.getUser())
                .role(userRole.getRole())
                .organization(userRole.getOrganization())
                .startDate(DateTime.toString(userRole.getStartDate()))
                .endDate(DateTime.toString(userRole.getEndDate()))
                .description(userRole.getDescription())
                .build();
    }

    public GenericResponse deleteUserRole(Integer id) {
        GenericResponse response;
        if (!userRoleRepository.existsById(id)) {
            throw new NotFoundException("User-role with id: " + id + " not found");
        }
        response = new GenericResponse(userRoleRepository.findById(id), "User-permission with id: " + id
                + " successfully deleted", HttpServletResponse.SC_OK);
        List<Authorization> authorizations = authorizationRepository.findAllByUserRoleId(id);
        authorizationRepository.deleteAll(authorizations);
        userRoleRepository.deleteById(id);
        return response;
    }

    public List<GenericResponse> createUserRoles(List<UserRoleRequest> request) {
        List<GenericResponse> response = new ArrayList<>();
        for (UserRoleRequest req : request) {
            if (RequestValidator.isObjectValid(req) != null) {
                response.add(new GenericResponse(req, RequestValidator.isObjectValid(req), HttpServletResponse.SC_BAD_REQUEST));
            } else {
                User user = userRepository.findById(req.getUserId()).orElse(null);
                Role role = roleRepository.findById(req.getRoleId()).orElse(null);
                Organization organization = organizationRepository.findById(req.getOrganizationId()).orElse(null);
                if (user == null) {
                    response.add(new GenericResponse(req, "The userId " + req.getUserId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
                if (role == null) {
                    response.add(new GenericResponse(req, "The permissionId " + req.getRoleId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
                if (organization == null) {
                    response.add(new GenericResponse(req, "The organizationId " + req.getOrganizationId() + " not found", HttpServletResponse.SC_BAD_REQUEST));
                }
                if (user != null && role != null && organization != null) {
                    UserRole userRole = UserRole.builder()
                            .user(user)
                            .role(role)
                            .organization(organization)
                            .startDate(DateTime.toTimestamp(req.getStartDate()))
                            .endDate(DateTime.toTimestamp(req.getEndDate()))
                            .description(req.getDescription())
                            .build();
                    userRole = userRoleRepository.save(userRole);

                    List<RolePermission> listOfRolePermission = rolePermissionRepository.findAllByRoleId(role.getId());
                    for (RolePermission rolePermission : listOfRolePermission) {
                        Timestamp[] overlapInterval = DateTime.overleap(userRole.getStartDate(), userRole.getEndDate(), rolePermission.getStartDate(), rolePermission.getEndDate());
                        if (overlapInterval != null) {
                            Authorization authorization = Authorization.builder()
                                    .startDate(overlapInterval[0])
                                    .endDate(overlapInterval[1])
                                    .user(user)
                                    .role(role)
                                    .permission(rolePermission.getPermission())
                                    .userRole(userRole)
                                    .rolePermission(rolePermission)
                                    .build();
                            authorizationRepository.save(authorization);
                        }
                    }
                    response.add(new GenericResponse(userRole, "User-role successfully created", HttpServletResponse.SC_CREATED));
                }
            }
        }
        return response;
    }

    public List<UserRoleResponse> getUserRoles() {
        List<UserRole> userRoles = userRoleRepository.findAll();
        if (userRoles.isEmpty()) {
            throw new NotFoundException("No user-role was found");
        }

        List<UserRoleResponse> userRoleResponses = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            userRoleResponses.add(UserRoleResponse.builder()
                    .id(userRole.getId())
                    .user(userRole.getUser())
                    .role(userRole.getRole())
                    .organization(userRole.getOrganization())
                    .startDate(DateTime.toString(userRole.getStartDate()))
                    .endDate(DateTime.toString(userRole.getEndDate()))
                    .description(userRole.getDescription())
                    .build()
            );
        }
        return userRoleResponses;
    }

    public List<GenericResponse> deleteUserRoles(List<Integer> ids) {
        List<GenericResponse> response = new ArrayList<>();
        for (Integer id : ids) {
            if (userRoleRepository.existsById(id)) {
                response.add(new GenericResponse(userRoleRepository.findById(id), "User-role with id: " + id + " successfully deleted", HttpServletResponse.SC_OK));
                List<Authorization> authorizations = authorizationRepository.findAllByUserRoleId(id);
                authorizationRepository.deleteAll(authorizations);
                userRoleRepository.deleteById(id);
            } else {
                response.add(new GenericResponse(null, "User-role with id: " + id + " not found", HttpServletResponse.SC_NOT_FOUND));
            }
        }
        return response;
    }
}
