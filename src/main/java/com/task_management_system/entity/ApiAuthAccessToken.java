package com.task_management_system.entity;

import com.task_management_system.misc.TokenType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "ApiAuthAccessToken")
@Table(name = "api_auth_access_token")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ApiAuthAccessToken extends AbstractEntity {
    // region PROPERTIES
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name = "expires")
    private LocalDateTime expires;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne(optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "api_auth_access_token_api_user_detail_fk"))
    private APIUserDetail apiUserDetail;
    // endregion
}
