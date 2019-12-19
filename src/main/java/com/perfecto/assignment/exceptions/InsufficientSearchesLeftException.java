package com.perfecto.assignment.exceptions;

public class InsufficientSearchesLeftException extends Exception {

    public InsufficientSearchesLeftException() {
        super("You have reached your accounts limit of searches - please upgrade the plan to create more searches this month");
    }

}
