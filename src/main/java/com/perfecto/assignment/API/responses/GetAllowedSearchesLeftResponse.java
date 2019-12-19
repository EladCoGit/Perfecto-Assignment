package com.perfecto.assignment.API.responses;

public class GetAllowedSearchesLeftResponse {

    public String number_of_searches_left;
    public int user_id;

    public GetAllowedSearchesLeftResponse(String numSearchesLeft, int userId) {
        this.number_of_searches_left = numSearchesLeft;
        this.user_id = userId;
    }
}
