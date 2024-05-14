package com.cnems.dto;

public class SuccessToken {
    private boolean success;
    private String token;

    public boolean isSuccess() {
        return success;
    }

    public SuccessToken(boolean success, String token) {
        this.success = success;
        this.token = token;
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
}
