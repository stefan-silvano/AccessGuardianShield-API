package com.cybersecurity.iam.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequest {

    @NotNull(message = "The name cannot be null")
    @Size(min = 2, message = "The name must have a minimum length of 2 characters")
    private String name;

    @NotNull(message = "The description cannot be null")
    @Size(min = 2, message = "The description must have a minimum length of 2 characters")
    private String description;

    @NotNull(message = "The riskLevel cannot be null")
    @Size(min = 3, message = "The riskLevel must have a minimum length of 3 characters")
    private String riskLevel;

    @NotNull(message = "The containerId cannot be null")
    @Positive(message = "The containerId must be a positive value")
    private Integer containerId;
}
