package API.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class GraphQlClient {

    private static final String BASE_URL = "https://lmsb2b.do.dibimbing.id";

    public static Response sendQuery(String query, Object variables) {
        Map<String, Object> graphqlBody = new HashMap<>();
        graphqlBody.put("query", query);
        graphqlBody.put("variables", variables);

        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(graphqlBody)
                .when()
                .post("/graphql")
                .then()
                .log().all()
                .extract().response();
    }
}