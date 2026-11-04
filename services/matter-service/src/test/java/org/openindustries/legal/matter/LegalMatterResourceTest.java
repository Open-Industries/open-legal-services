package org.openindustries.legal.matter;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class LegalMatterResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"matterType\":\"LITIGATION\",\"clientReference\":\"CLI-2026-556677\",\"jurisdictionCode\":\"MX-CDMX\"}")
                .when().post("/api/v1/matters")
                .then().statusCode(201)
                .body("status", is("OPEN"))
                .body("clientReferenceMasked", endsWith("6677"))
                .extract().path("id");

        given().when().get("/api/v1/matters/" + id)
                .then().statusCode(200).body("status", is("OPEN"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"matterType\":\"LITIGATION\",\"clientReference\":\"CLI-2026-556677\",\"jurisdictionCode\":\"MX-CDMX\"}")
                .when().post("/api/v1/matters")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/matters/" + id + "/suspend")
                .then().statusCode(200).body("status", is("ON_HOLD"));
    }
}
