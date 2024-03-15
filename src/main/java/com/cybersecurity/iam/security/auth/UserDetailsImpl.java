package com.cybersecurity.iam.security.auth;

import com.cybersecurity.iam.database.relationship.Authorization;
import com.cybersecurity.iam.repository.UserPermissionRepository;
import com.cybersecurity.iam.service.UserPermissionService;
import jakarta.transaction.Transactional;
import lombok.*;
import com.cybersecurity.iam.database.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Integer id;

    private final String username;

    @JsonIgnore
    private String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities;
        if (user.getAuthorizations() != null) {
            authorities = user.getAuthorizations()
                    .stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getPermission().getName()))
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            authorities = new ArrayList<>();
        }

        //System.out.println(authorities);
        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailsImpl)) return false;
        return id != null && id.equals(((UserDetailsImpl) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "UserDetailsImpl{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
