package com.cybersecurity.iam.payload.response;

import com.cybersecurity.iam.database.entity.Address;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationResponse {
    private Integer id;

    private String name;

    private String description;

    private String phoneNumber;

    private Address address;
}
