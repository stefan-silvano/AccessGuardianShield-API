package com.cybersecurity.iam.exception;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationExceptionResponse {
    private String path;
    private String error;
    private String message;
    private int status;
}
