package com.mvucevski.usermanagement.api.payload;

import com.mvucevski.usermanagement.domain.UserId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthUserResponse {
    private UserId userId;

    private String username;

    private String fullName;

    private boolean isMembershipExpired;

    private String role;

    public AuthUserResponse(UserId userId, String username, String fullName, boolean isMembershipExpired, String role) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.isMembershipExpired = isMembershipExpired;
        this.role = role;
    }
}
