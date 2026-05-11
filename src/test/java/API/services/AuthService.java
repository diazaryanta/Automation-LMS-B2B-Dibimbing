package API.services;

import API.client.GraphQlClient;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    public static Response login(String email, String password, String companyId) {
        String mutationQuery = "mutation login($usernameOrEmail: String!, $password: String!, $companyId: String!) { " +
                "login(usernameOrEmail: $usernameOrEmail, password: $password, companyId: $companyId) { " +
                "user { username id role __typename } " +
                "errors { field message __typename } " +
                "__typename } }";

        Map<String, String> variables = new HashMap<>();
        variables.put("usernameOrEmail", email);
        variables.put("password", password);
        variables.put("companyId", companyId);

        return GraphQlClient.sendQuery(mutationQuery, variables);
    }
}