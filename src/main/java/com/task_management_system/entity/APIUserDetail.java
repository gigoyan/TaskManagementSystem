package com.task_management_system.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "APIUserDetail")
@Table(name = "api_user_detail")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"roles"})
public class APIUserDetail extends AbstractEntity implements UserDetails {
    // region PROPERTIES
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "non_expired")
    private Boolean accountNonExpired;

    @Column(name = "non_locked")
    private Boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "approved", nullable = false)
    private Boolean approved;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "api_user_detail_role",
            joinColumns = @JoinColumn(name = "api_user_detail_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "user_api_user_detail_fk"))
    private User user;

    // endregion

    // region IMPLEMENTATIONS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired == null ? false : this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked == null ? false : this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired == null ? false : this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled == null ? false : this.enabled;
    }
    // endregion
}
