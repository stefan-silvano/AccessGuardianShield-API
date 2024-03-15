package com.cybersecurity.iam.payload.response;

import com.cybersecurity.iam.database.entity.Container;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {
    private Integer id;

    private String name;

    private String description;

    private String riskLevel;

    private Container container;
}
