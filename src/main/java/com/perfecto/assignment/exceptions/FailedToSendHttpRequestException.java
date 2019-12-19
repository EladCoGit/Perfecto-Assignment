package com.perfecto.assignment.exceptions;

public class FailedToSendHttpRequestException extends Exception {

    public FailedToSendHttpRequestException() {
        super("Failed to send HTTP POST request");
    }

}
