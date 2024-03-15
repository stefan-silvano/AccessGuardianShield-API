package com.cybersecurity.iam.payload.response;

import com.cybersecurity.iam.database.entity.Organization;
import com.cybersecurity.iam.database.entity.Permission;
import com.cybersecurity.iam.database.entity.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionResponse {

    private Integer id;

    private Role role;

    private Permission permission;

    private Organization organization;

    private String startDate;

    private String endDate;

    private String description;
}
