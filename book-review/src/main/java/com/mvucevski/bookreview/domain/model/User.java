package com.mvucevski.bookreview.domain.model;


import lombok.Getter;

//
//@Getter
//public class User implements UserDetails {
//
//    private UserId userId;
//
//    private String username;
//
//    private String fullName;
//
//    private LocalDateTime membershipExpirationDate;
//
//    private String role;
//
//    public User() {
//    }
//
//    public User(UserId userId, String username, String fullName, LocalDateTime membershipExpirationDate, String role) {
//        this.userId = userId;
//        this.username = username;
//        this.fullName = fullName;
//        this.membershipExpirationDate = membershipExpirationDate;
//        this.role = role;
//    }
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
//    }
//
//    @Override
//    public String getPassword() {
//        return "";
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    @JsonIgnore
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    @JsonIgnore
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    @JsonIgnore
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    @JsonIgnore
//    public boolean isEnabled() {
//        return true;
//    }
//
//
//
//}


@Getter
public class User {

    private UserId userId;

    private String username;

    private String fullName;

    private boolean isMembershipExpired;

    private String role;

    public User() {
    }

    public User(UserId userId, String username, String fullName, boolean isMembershipExpired, String role) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.isMembershipExpired = isMembershipExpired;
        this.role = role;
    }



}
