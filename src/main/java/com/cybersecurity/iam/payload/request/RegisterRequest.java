package com.cybersecurity.iam.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull(message = "The firstname cannot be null")
    @Size(min = 2, message = "The firstname must have a minimum length of 2 characters")
    private String firstname;

    @NotNull(message = "The lastname cannot be null")
    @Size(min = 2, message = "The lastname must have a minimum length of 2 characters")
    private String lastname;

    @NotNull(message = "The email cannot be null")
    @Email(message = "The email invalid due to pattern")
    private String email;

    @NotNull(message = "The password cannot be null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password length must be 8 characters and contain: 1 upper, 1 lower and 1 special character")
    private String password;
}
