package org.example.models.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Admin {
    @JsonProperty("admin_id")
    private int admin_id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("password")
    private String password;

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
