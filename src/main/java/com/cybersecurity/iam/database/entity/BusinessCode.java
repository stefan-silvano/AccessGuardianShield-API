package com.cybersecurity.iam.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "business_code")
@Entity(name = "BusinessCode")
public class BusinessCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessCode)) return false;
        return id != null && id.equals(((BusinessCode) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "BusinessCode{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", role=" + role +
                '}';
    }
}
