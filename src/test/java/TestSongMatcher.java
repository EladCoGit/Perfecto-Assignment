import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.perfecto.assignment.API.ApiCallHandler;
import com.perfecto.assignment.db.connectivity.DBFaker;
import com.perfecto.assignment.db.structure.Plan;
import com.perfecto.assignment.exceptions.FailedToSendHttpRequestException;
import com.perfecto.assignment.exceptions.UserCreationException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class TestSongMatcher {

    DBFaker db;
    ApiCallHandler apiCallHandler;

    @Before
    public void init() {
        db = DBFaker.getInstance();
        apiCallHandler = new ApiCallHandler();
        apiCallHandler.startServer();
    }

    public void generalNonCountableTestBody(Plan plan) {
        int userId;
        try {
            userId = db.addUserWithPlan("user" + plan , plan);
        } catch (UserCreationException e) {
            TestCase.fail("Failed to create user for test " + plan);
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        try {
            for (int i = 0 ; i < 1000; i++) {
                params.put("song_name", plan + " song" + i);
                HttpResponse<JsonNode> jsonNodeHttpResponse = HTTPRequestHelper.sendHttpPostRequest(
                        "/songs/api/v0/getSimilarSongs",
                        params
                );
                Assert.assertEquals(jsonNodeHttpResponse.getCode(), 200);
            }
        } catch (FailedToSendHttpRequestException e) {
            TestCase.fail("Failed to send requests for similar songs");
        }
    }


    public void generalCountableTestBody(Plan plan) {
        int userId;
        try {
            userId = db.addUserWithPlan("user" + plan , plan);
        } catch (UserCreationException e) {
            TestCase.fail("Failed to create user for test " + plan);
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        try {
            for (int i = 0 ; i < plan.getValue() +1; i++) {
                params.put("song_name", plan + " song" + i);
                HttpResponse<JsonNode> jsonNodeHttpResponse = HTTPRequestHelper.sendHttpPostRequest(
                        "/songs/api/v0/getSimilarSongs",
                        params
                );
                if (i < plan.getValue() ) {
                    Assert.assertEquals(jsonNodeHttpResponse.getCode(), 200);
                } else {
                    Assert.assertEquals(jsonNodeHttpResponse.getCode(), 402);
                }
            }
        } catch (FailedToSendHttpRequestException e) {
            TestCase.fail("Failed to send requests for similar songs");
        }
    }

    @Test
    public void testFreeTrial() {
        generalCountableTestBody(Plan.FreeTrial);
    }

    @Test
    public void testFifty() {
        generalCountableTestBody(Plan.Fifty);
    }

    @Test
    public void testHundred() {
        generalCountableTestBody(Plan.Hundred);
    }

    @Test
    public void testUnlimited() {
        generalNonCountableTestBody(Plan.Unlimited);
    }

    @Test
    public void testPerUse() {
        generalNonCountableTestBody(Plan.PerUse);
    }

    @After
    public void stop() {
        apiCallHandler.stopServer();
    }


}
