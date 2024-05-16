package com.example.security.sec1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String permissionName;

    @OneToMany(mappedBy = "permission")
    private List<UserFeaturePermission> userFeaturePermissionList;
}
