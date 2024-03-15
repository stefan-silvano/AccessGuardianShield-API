package com.cybersecurity.iam.service;

import com.cybersecurity.iam.database.relationship.Authorization;
import com.cybersecurity.iam.payload.response.AuthorizationResponse;
import com.cybersecurity.iam.repository.AuthorizationRepository;
import com.cybersecurity.iam.utility.DateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    public List<AuthorizationResponse> getAuthorizations() {
        List<Authorization> authorizations = authorizationRepository.findAll();

        List<AuthorizationResponse> authorizationResponses = new ArrayList<>();
        for (Authorization authorization : authorizations) {
            authorizationResponses.add(AuthorizationResponse.builder()
                    .id(authorization.getId())
                    .user(authorization.getUser())
                    .permission(authorization.getPermission())
                    .role(authorization.getRole())
                    .userRole(authorization.getUserRole())
                    .userPermission(authorization.getUserPermission())
                    .rolePermission(authorization.getRolePermission())
                    .startDate(DateTime.toString(authorization.getStartDate()))
                    .endDate(DateTime.toString(authorization.getEndDate()))
                    .build()
            );
        }
        return authorizationResponses;
    }
}
