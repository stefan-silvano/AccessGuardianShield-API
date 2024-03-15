package com.cybersecurity.iam.payload.response;

import com.cybersecurity.iam.database.entity.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessCodeResponse {
    private Integer id;

    private String code;

    private Role role;
}
