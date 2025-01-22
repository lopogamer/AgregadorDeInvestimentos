package com.luanr.agregadorinvestimentos.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "roles_tb")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false, updatable = false, unique = true)
    private Long roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public enum Values {

        ADMIN(1L),
        BASIC(2L);

        final long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }

        public long getRoleId() {
            return roleId;
        }
    }
}
