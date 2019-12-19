package com.perfecto.assignment.API;

import com.google.gson.Gson;
import com.perfecto.assignment.API.responses.AddUserResponse;
import com.perfecto.assignment.API.responses.GetAllowedSearchesLeftResponse;
import com.perfecto.assignment.API.responses.GetMatchingSongsResponse;
import com.perfecto.assignment.exceptions.InsufficientSearchesLeftException;
import com.perfecto.assignment.exceptions.UserCreationException;
import com.perfecto.assignment.entitlement.EntitlementService;
import com.perfecto.assignment.songs.MatchingSongsService;
import com.perfecto.assignment.songs.Song;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.security.InvalidKeyException;
import java.util.ArrayList;

/**
 * handles all API calls
 */
public class ApiCallHandler {

    private static Gson gSon = new Gson();

    private Javalin app;

    public void startServer() {
        app = Javalin.create().start(12345);
        app.post("/enforcement/api/v0/getNumberOfAllowedResults", handleGetNumAllowedResults);
        app.post("/songs/api/v0/getSimilarSongs", handleGetSimilarSongsApiCall);
        app.post("/user/api/v0/addUser", handleAddUserWithPlan);
    }

    public void stopServer() {
        app.stop();
    }

    /**
     * returns the number of searches left for a given user id
     */
    public static Handler handleGetNumAllowedResults = ctx -> {
        int userId;
        try {
            userId = Integer.parseInt(getPostQueryParameterByKey(ctx, "user_id"));
        } catch (Exception e) {
            setContextWithFailStatus(ctx);
            ctx.result("Expected user_id as params, yet was not supplied");
            return;
        }

        String numOfSearchesLeft = EntitlementService.getUserNumOfSearchesLeft(userId);
        GetAllowedSearchesLeftResponse response = new GetAllowedSearchesLeftResponse(numOfSearchesLeft, userId);
        setContextWithSuccessStatus(ctx);
        ctx.result(gSon.toJson(response));

    };

    /**
     * Adds user with given plan to the DB
     */
    public static Handler handleAddUserWithPlan = ctx -> {
        String username;
        String plan;
        try {
            username = getPostQueryParameterByKey(ctx, "username");
            plan = getPostQueryParameterByKey(ctx, "plan");
        } catch (Exception e) {
            setContextWithFailStatus(ctx);
            ctx.result("Expected username and plan as params, yet could not find all of them");
            return;
        }
        try {
            int userId = EntitlementService.addUserWithPlan(username, plan);
            AddUserResponse response = new AddUserResponse(username, userId);
            ctx.result(gSon.toJson(response));
            setContextWithSuccessStatus(ctx);
        } catch (UserCreationException e) {
            setContextWithFailStatus(ctx);
            ctx.result(e.getMessage());
        }
    };

    /**
     * returns a list up to 5 similar songs to the given song
     * works under the restrictions applied for the user id
     */
    public static Handler handleGetSimilarSongsApiCall = ctx -> {
        int userId;
        String songName;
        try {
             userId = Integer.parseInt(getPostQueryParameterByKey(ctx, "user_id"));
             songName = getPostQueryParameterByKey(ctx, "song_name");
        } catch (Exception e) {
            setContextWithFailStatus(ctx);
            ctx.result("Expected user_id and song_name as params, yet could not find all of them");
            return;
        }
        try {
            ArrayList<Song> matchingSongs = MatchingSongsService.getMatchingSongs(userId, songName);
            setContextWithSuccessStatus(ctx);
            GetMatchingSongsResponse response = new GetMatchingSongsResponse(matchingSongs);
            ctx.result(gSon.toJson(response));
        } catch (InsufficientSearchesLeftException e) {
            setContextWithInsufficientFundsStatus(ctx);
            ctx.result(e.getMessage());
        }
    };

    private static String getPostQueryParameterByKey(Context ctx, String key) throws InvalidKeyException {
        try {
            return ctx.formParam(key);
        } catch (Exception e) {
            throw new InvalidKeyException(key);
        }
    }

    private static void setContextWithSuccessStatus(Context ctx) {
        ctx.status(200);
    }

    private static void setContextWithFailStatus(Context ctx) {
        ctx.status(400);
    }

    private static void setContextWithInsufficientFundsStatus(Context ctx) {
        ctx.status(402);
    }


}
