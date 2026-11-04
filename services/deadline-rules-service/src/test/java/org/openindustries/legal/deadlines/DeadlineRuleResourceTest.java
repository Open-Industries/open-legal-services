package org.openindustries.legal.deadlines;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class DeadlineRuleResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"litigation-standard\",\"matterType\":\"LITIGATION\",\"maxFilingWindowDays\":90,\"maxExtensionCount\":2,\"priority\":1}")
                .when().post("/api/v1/deadline-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/deadline-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"matterType\":\"LITIGATION\",\"requiredMaxExtensionCount\":3,\"requiredMaxFilingWindowDays\":60}")
                .when().post("/api/v1/deadline-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"matterType\":\"FAMILY\",\"requiredMaxExtensionCount\":3,\"requiredMaxFilingWindowDays\":60}")
                .when().post("/api/v1/deadline-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
