package com.cybersecurity.iam.payload.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ContainerRequest {

    @NotNull(message = "The name cannot be null")
    @Size(min = 2, message = "The name must have a minimum length of 3 characters")
    private String name;

    @NotNull(message = "The description cannot be null")
    @Size(min = 3, message = "The description must have a minimum length of 3 characters")
    private String description;

    @NotNull(message = "The containerId cannot be null")
    @Positive(message = "The containerId must be a positive value")
    private Integer parentId;
}
