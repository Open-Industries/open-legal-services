package org.openindustries.legal.functions.matterrisk;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"matterId\":\"mt-1\",\"deadlinePressureIndex\":8,\"documentGapIndex\":5,\"conflictIndex\":0}")
                .when().post("/matterRiskScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"matterId\":\"mt-2\",\"deadlinePressureIndex\":93,\"documentGapIndex\":71,\"conflictIndex\":64}")
                .when().post("/matterRiskScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
