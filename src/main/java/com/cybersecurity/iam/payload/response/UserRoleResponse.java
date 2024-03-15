package com.cybersecurity.iam.payload.response;

import com.cybersecurity.iam.database.entity.Organization;
import com.cybersecurity.iam.database.entity.Role;
import com.cybersecurity.iam.database.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponse {

    private Integer id;

    private User user;

    private Role role;

    private Organization organization;

    private String startDate;

    private String endDate;

    private String description;
}
