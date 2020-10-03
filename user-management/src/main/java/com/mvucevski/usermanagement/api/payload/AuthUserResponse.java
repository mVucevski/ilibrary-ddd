package com.mvucevski.usermanagement.api.payload;

import com.mvucevski.usermanagement.domain.UserId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthUserResponse {
    private UserId userId;

    private String username;

    private String fullName;

    private LocalDateTime membershipExpirationDate;

    private String role;

    public AuthUserResponse(UserId userId, String username, String fullName, LocalDateTime membershipExpirationDate, String role) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.membershipExpirationDate = membershipExpirationDate;
        this.role = role;
    }
}
