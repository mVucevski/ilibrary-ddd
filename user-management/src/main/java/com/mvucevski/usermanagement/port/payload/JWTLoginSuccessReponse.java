package com.mvucevski.usermanagement.port.payload;

public class JWTLoginSuccessReponse {
    private boolean success;
    private String token;
    private String role;

    public JWTLoginSuccessReponse(boolean success, String token, String role) {
        this.success = success;
        this.token = token;
        this.role = role;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "JWTLoginSucessReponse{" +
                "success=" + success +
                ", token='" + token + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
