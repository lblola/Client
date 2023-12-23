package org.example.models.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Client {
    @JsonProperty("client_id")
    private int client_id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private String address;
    @JsonProperty("password")
    private String password;

    public Client(String name, String address, String password) {
        this.name = name;
        this.address = address;
        this.password = password;
    }

    public Client() {
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
