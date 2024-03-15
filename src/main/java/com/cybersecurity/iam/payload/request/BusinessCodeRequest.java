package com.cybersecurity.iam.payload.request;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BusinessCodeRequest {

    @NotNull(message = "The code cannot be null")
    @Size(min = 3, message = "The code must have a minimum length of 3 characters")
    private String code;

    @NotNull(message = "The roleId cannot be null")
    @Positive(message = "The roleId must be a positive value")
    private Integer roleId;
}
