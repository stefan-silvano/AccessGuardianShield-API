package com.cybersecurity.iam.payload.response;

import com.cybersecurity.iam.database.entity.Container;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

    private Integer id;

    private String name;

    private String description;

    private String type;

    private String riskLevel;

    private Container container;
}