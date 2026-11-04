package org.openindustries.legal.functions.deadlinecheck;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"matterId\":\"mt-1\",\"measuredMissedDeadlines\":0,\"targetMissedDeadlines\":0,\"measuredOnTimePct\":100.0,\"targetOnTimePct\":100.0}")
                .when().post("/deadlineComplianceEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"matterId\":\"mt-2\",\"measuredMissedDeadlines\":2,\"targetMissedDeadlines\":0,\"measuredOnTimePct\":100.0,\"targetOnTimePct\":100.0}")
                .when().post("/deadlineComplianceEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
