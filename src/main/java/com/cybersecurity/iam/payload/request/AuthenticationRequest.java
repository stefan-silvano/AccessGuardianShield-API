package com.cybersecurity.iam.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "The email cannot be null")
    @Email(message = "The email invalid due to pattern")
    private String email;

    @NotNull(message = "The password cannot be null")
    private String password;
}
