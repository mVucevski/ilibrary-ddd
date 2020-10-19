package com.mvucevski.usermanagement.port.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mvucevski.usermanagement.domain.model.UserId;
import lombok.Getter;

@Getter
public class UserDTO {

    private UserId id;

    private String username;

    private String fullName;

    @JsonProperty("isMembershipExpired")
    private Boolean isMembershipExpired;

    private String role;

    public UserDTO(UserId id, String username, String fullName, Boolean isMembershipExpired, String role) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.isMembershipExpired = isMembershipExpired;
        this.role = role;
    }
}
