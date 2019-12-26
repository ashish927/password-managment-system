package com.assertion.passwordmanager.model;

public class PasswordGenerationRequest {
    private String siteName;
    private String password;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
