package com.cybersecurity.iam.payload.response;

import com.cybersecurity.iam.database.entity.Permission;
import com.cybersecurity.iam.database.entity.Role;
import com.cybersecurity.iam.database.entity.User;
import com.cybersecurity.iam.database.relationship.RolePermission;
import com.cybersecurity.iam.database.relationship.UserPermission;
import com.cybersecurity.iam.database.relationship.UserRole;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationResponse {
    private Integer id;
    private User user;
    private Role role;
    private Permission permission;
    private UserRole userRole;
    private UserPermission userPermission;
    private RolePermission rolePermission;
    private String startDate;
    private String endDate;

}
