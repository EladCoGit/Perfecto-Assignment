package com.perfecto.assignment.API.responses;

public class AddUserResponse {

    public String username;
    public int user_id;

    public AddUserResponse(String username, int userId) {
        this.username = username;
        this.user_id = userId;
    }
}
