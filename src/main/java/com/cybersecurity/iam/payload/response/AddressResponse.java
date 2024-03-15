package com.cybersecurity.iam.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private Integer id;
    private String country;
    private String city;
    private String street;
    private Integer number;
    private Integer postalCode;
}
