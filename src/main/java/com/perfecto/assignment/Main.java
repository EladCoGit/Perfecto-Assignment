package com.perfecto.assignment;

import com.perfecto.assignment.API.ApiCallHandler;

public class Main {

    public static void main(String[] args) {
        ApiCallHandler apiCallHandler = new ApiCallHandler();
        apiCallHandler.startServer();
    }
}
