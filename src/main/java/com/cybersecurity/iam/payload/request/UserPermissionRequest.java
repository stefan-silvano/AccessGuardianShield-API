package com.cybersecurity.iam.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionRequest {

    @NotNull(message = "The roleId cannot be null")
    @Positive(message = "The roleId must be a positive value")
    private Integer userId;

    @NotNull(message = "The permissionId cannot be null")
    @Positive(message = "The permissionId must be a positive value")
    private Integer permissionId;

    @NotNull(message = "The startDate cannot be null")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "The startDate invalid due to pattern")
    private String startDate;

    @NotNull(message = "The endDate cannot be null")
    @Pattern(regexp = "^^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "The endDate invalid due to pattern")
    private String endDate;

    @NotNull(message = "The description cannot be null")
    @Size(min = 3, message = "The description must have a minimum length of 3 characters")
    private String description;
}
