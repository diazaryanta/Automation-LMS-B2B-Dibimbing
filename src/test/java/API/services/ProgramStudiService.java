package API.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ProgramStudiService {

    public static Response createProgramStudi(String sessionId, Map<String, Object> inputVariables) throws IOException {

        // Membaca file GraphQL yang baru dibuat
        String graphqlPayload = new String(Files.readAllBytes(Paths.get("src/test/resources/graphql/mutations/CreateDivision.graphql")));

        Map<String, Object> graphqlBody = new HashMap<>();
        graphqlBody.put("query", graphqlPayload);

        Map<String, Object> variables = new HashMap<>();
        variables.put("input", inputVariables);
        graphqlBody.put("variables", variables);

        return RestAssured.given()
                .log().all() // Menampilkan semua log request yang dikirim
                .baseUri("https://lmsb2b.do.dibimbing.id")
                .header("Content-Type", "application/json")
                .cookie("sid_b2b", sessionId)
                .body(graphqlBody)
                .when()
                .post("/graphql")
                .then()
                .log().all() // Menampilkan semua log response dari server
                .extract().response();
    }
}