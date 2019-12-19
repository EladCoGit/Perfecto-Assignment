package com.perfecto.assignment.exceptions;

public class UserCreationException extends Exception {

    public UserCreationException(String username, String reason) {
        super("Failed to create user for username " + username + ", due to " + reason);
    }
}
