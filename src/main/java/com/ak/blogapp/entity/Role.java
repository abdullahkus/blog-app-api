package com.ak.blogapp.entity;

import com.ak.blogapp.enums.ERole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "ROLES")
public class Role  implements GrantedAuthority {
    @Id
    @UuidGenerator
    private String id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}
