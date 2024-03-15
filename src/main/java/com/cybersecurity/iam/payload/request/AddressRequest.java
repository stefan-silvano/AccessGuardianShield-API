package com.cybersecurity.iam.payload.request;

import lombok.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddressRequest {

    @NotNull(message = "The country cannot be null")
    @Size(min = 2, message = "The country must have a minimum length of 2 characters")
    private String country;

    @NotNull(message = "The city cannot be null")
    @Size(min = 2, message = "The code must have a minimum length of 2 characters")
    private String city;

    @NotNull(message = "The street cannot be null")
    @Size(min = 3, message = "The street must have a minimum length of 3 characters")
    private String street;

    @NotNull(message = "The number cannot be null")
    @Positive(message = "The number must be a positive value")
    private Integer number;

    @NotNull(message = "The postalCode cannot be null")
    @Positive(message = "The number must be a positive value")
    private Integer postalCode;
}
