package com.cybersecurity.iam.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrganizationRequest {

    @NotNull(message = "The name cannot be null")
    @Size(min = 2, message = "The name must have a minimum length of 2 characters")
    private String name;

    @NotNull(message = "The description cannot be null")
    @Size(min = 3, message = "The description must have a minimum length of 3 characters")
    private String description;

    @NotNull(message = "The phoneNumber cannot be null")
    @Size(min = 4, max = 12, message = "The phoneNumber must have a minimum of 4 characters and maximum of 12 characters without prefix")
    private String phoneNumber;

    @NotNull(message = "The addressId cannot be null")
    @Positive(message = "The addressId must be a positive value")
    private Integer addressId;
}
