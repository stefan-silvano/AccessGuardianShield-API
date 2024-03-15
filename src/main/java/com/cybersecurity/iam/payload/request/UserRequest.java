package com.cybersecurity.iam.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "The firstName cannot be null")
    @Size(min = 2, message = "The firstName must have a minimum length of 2 characters")
    private String firstName;

    @NotNull(message = "The lastName cannot be null")
    @Size(min = 2, message = "The lastName must have a minimum length of 2 characters")
    private String lastName;

    @NotNull(message = "The email cannot be null")
    @Email(message = "The email invalid due to pattern")
    private String email;

    @NotNull(message = "The password cannot be null")
    @Pattern(regexp = "^((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,})?$", message = "Password length must be 8 characters and contain: 1 upper, 1 lower and 1 special character")
    private String password;
}
