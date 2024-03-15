package com.cybersecurity.iam.payload.response;

import com.cybersecurity.iam.database.entity.Permission;
import com.cybersecurity.iam.database.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionResponse {

    private Integer id;

    private User user;

    private Permission permission;

    private String startDate;

    private String endDate;

    private String description;
}
