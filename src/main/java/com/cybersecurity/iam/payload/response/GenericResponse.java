package com.cybersecurity.iam.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {

    Object object;
    String message;
    Integer status;
}
