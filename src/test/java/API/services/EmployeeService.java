package API.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EmployeeService {

    public static Response createEmployee(String sessionId, Map<String, Object> inputVariables) throws IOException {

        String graphqlPayload = new String(Files.readAllBytes(Paths.get("src/test/resources/graphql/mutations/CreateEmployee.graphql")));

        Map<String, Object> graphqlBody = new HashMap<>();
        graphqlBody.put("query", graphqlPayload);

        Map<String, Object> variables = new HashMap<>();
        variables.put("input", inputVariables);
        graphqlBody.put("variables", variables);

        return RestAssured.given()
                .log().all()
                .baseUri("https://lmsb2b.do.dibimbing.id")
                .header("Content-Type", "application/json")
                .cookie("sid_b2b", sessionId)
                .body(graphqlBody)
                .when()
                .post("/graphql")
                .then()
                .log().all()
                .extract().response();
    }

    //======================
    // --- EDIT EMPLOYEE ---
    //======================

    public static Response updateEmployee(String sessionId, String targetId, Map<String, Object> inputVariables) throws IOException {

        String graphqlPayload = new String(Files.readAllBytes(Paths.get("src/test/resources/graphql/mutations/UpdateEmployee.graphql")));

        Map<String, Object> graphqlBody = new HashMap<>();
        graphqlBody.put("query", graphqlPayload);

        Map<String, Object> variables = new HashMap<>();
        variables.put("input", inputVariables);
        variables.put("id", targetId);

        graphqlBody.put("variables", variables);

        return RestAssured.given()
                .log().all()
                .baseUri("https://lmsb2b.do.dibimbing.id")
                .header("Content-Type", "application/json")
                .cookie("sid_b2b", sessionId)
                .body(graphqlBody)
                .when()
                .post("/graphql")
                .then()
                .log().all()
                .extract().response();
    }

    //=========================
    // --- ACTIVATE ---
    //=========================

    public static Response activateEmployee(String sessionId, String targetId) throws IOException {

        String graphqlPayload = new String(Files.readAllBytes(Paths.get("src/test/resources/graphql/mutations/ActivateEmployee.graphql")));

        Map<String, Object> graphqlBody = new HashMap<>();
        graphqlBody.put("query", graphqlPayload);

        Map<String, Object> variables = new HashMap<>();
        variables.put("id", targetId);
        graphqlBody.put("variables", variables);

        return RestAssured.given()
                .log().all()
                .baseUri("https://lmsb2b.do.dibimbing.id")
                .header("Content-Type", "application/json")
                .cookie("sid_b2b", sessionId)
                .body(graphqlBody)
                .when()
                .post("/graphql")
                .then()
                .log().all()
                .extract().response();
    }

    //=========================
    // --- INACTIVATE ---
    //=========================

    public static Response inactivateEmployee(String sessionId, String targetId) throws IOException {

        String graphqlPayload = new String(Files.readAllBytes(Paths.get("src/test/resources/graphql/mutations/InactivateEmployee.graphql")));

        Map<String, Object> graphqlBody = new HashMap<>();
        graphqlBody.put("query", graphqlPayload);

        Map<String, Object> variables = new HashMap<>();
        variables.put("id", targetId);
        graphqlBody.put("variables", variables);

        return RestAssured.given()
                .log().all()
                .baseUri("https://lmsb2b.do.dibimbing.id")
                .header("Content-Type", "application/json")
                .cookie("sid_b2b", sessionId)
                .body(graphqlBody)
                .when()
                .post("/graphql")
                .then()
                .log().all()
                .extract().response();
    }

    //=====================
    // --- RESEND EMAIL ---
    //=====================

    public static Response resendEmail(String sessionId, String targetEmployeeId) throws IOException {

        String graphqlPayload = new String(Files.readAllBytes(Paths.get("src/test/resources/graphql/mutations/ResendEmail.graphql")));

        Map<String, Object> graphqlBody = new HashMap<>();
        graphqlBody.put("query", graphqlPayload);

        Map<String, Object> variables = new HashMap<>();
        variables.put("employeeId", targetEmployeeId);
        graphqlBody.put("variables", variables);

        return RestAssured.given()
                .log().all()
                .baseUri("https://lmsb2b.do.dibimbing.id")
                .header("Content-Type", "application/json")
                .cookie("sid_b2b", sessionId)
                .body(graphqlBody)
                .when()
                .post("/graphql")
                .then()
                .log().all()
                .extract().response();
    }

}