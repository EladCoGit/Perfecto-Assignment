import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.perfecto.assignment.exceptions.FailedToSendHttpRequestException;
import java.util.Map;


public class HTTPRequestHelper {

    public static HttpResponse<JsonNode> sendHttpPostRequest(String urlPostfix, Map<String, Object> postArgs) throws FailedToSendHttpRequestException {
        try {
            return Unirest.post("http://localhost:12345" + urlPostfix)
                    .header("accept", "application/json")
                    .fields(postArgs)
                    .asJson();
        } catch (Exception e) {
            System.out.println("Failed to send HTTP post request");
            throw new FailedToSendHttpRequestException();
        }
    }
}
